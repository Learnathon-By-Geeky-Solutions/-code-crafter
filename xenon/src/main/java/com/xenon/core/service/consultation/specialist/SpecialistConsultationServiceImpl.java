/*
package com.xenon.core.service.consultation.specialist;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.consultation.specialist.CreateSpecialistConsultationRequest;
import com.xenon.core.domain.request.consultation.specialist.SpecialistConsultationAppointmentRequest;
import com.xenon.core.domain.response.consultation.SlotResponse;
import com.xenon.core.service.common.BaseService;
import com.xenon.core.service.notification.NotificationService;
import com.xenon.core.service.scheduling.ScheduleConflictService;
import com.xenon.data.entity.consultation.STATUS;
import com.xenon.data.entity.consultation.specialist.SpecialistConsultation;
import com.xenon.data.entity.consultation.specialist.SpecialistConsultationAppointmentTable;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.doctor.SpecialistCategory;
import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.hospital.AVAILABILITY;
import com.xenon.data.entity.hospital.DAY;
import com.xenon.data.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpecialistConsultationServiceImpl extends BaseService implements SpecialistConsultationService {

    private final SpecialistConsultationRepository specialistConsultationRepository;
    private final SpecialistConsultationAppointmentTableRepository specialistConsultationAppointmentTableRepository;
    private final DoctorRepository doctorRepository;
    private final NotificationService notificationService;
    private final ScheduleConflictService scheduleConflictService;
    private final EmergencyConsultationRepository emergencyConsultationRepository;

    @Override
    @Transactional
    public ResponseEntity<?> createSpecialistConsultation(CreateSpecialistConsultationRequest request) {
        validateCreateSpecialistConsultationRequest(request);

        try {
            Doctor doctor = doctorRepository.findById(request.getDoctor())
                    .orElseThrow(() -> new ClientException("Doctor not found"));

            // Check for scheduling conflicts using the conflict service
            ResponseEntity<?> conflictResponse = scheduleConflictService.checkSpecialistScheduleConflict(
                    doctor.getId(),
                    request.getDate_day(),
                    request.getStartTime(),
                    request.getEndTime()
            );

            if (!conflictResponse.getStatusCode().is2xxSuccessful()) {
                return conflictResponse;
            }

            // If the doctor wants to enable specialist consultation, disable emergency consultations
            if (request.getAvailability() == AVAILABILITY.AVAILABLE) {
                ResponseEntity<?> disableResponse = scheduleConflictService.disableEmergencyConsultationForSpecialist(doctor.getId());
                if (!disableResponse.getStatusCode().is2xxSuccessful()) {
                    return disableResponse;
                }
            }

            SpecialistConsultation specialistConsultation = request.toEntity(doctor);
            specialistConsultationRepository.save(specialistConsultation);

            return success("Specialist consultation created successfully", specialistConsultation);
        } catch (Exception e) {
            log.error("Error creating specialist consultation: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> bookSpecialistConsultation(SpecialistConsultationAppointmentRequest request) {
        validateBookSpecialistConsultationRequest(request);

        try {
            SpecialistConsultation specialistConsultation = specialistConsultationRepository.findById(request.getSpecialistConsultationId())
                    .orElseThrow(() -> new ClientException("Specialist consultation not found"));

            // Check if the slot is available
            if (isSlotBooked(specialistConsultation.getId(), request.getConsultationDate(), request.getSlotStartTime(), request.getSlotEndTime())) {
                throw new ClientException("This time slot is already booked");
            }

            // Create appointment
            SpecialistConsultationAppointmentTable appointment = request.toEntity(getCurrentUser(), specialistConsultation);
            appointment.setAppointmentStatus(AppointmentStatus.PENDING); // Set as pending initially

            // Generate meeting link
            String meetingLink = generateMeetingLink();
            appointment.setMeetingLink(meetingLink);

            specialistConsultationAppointmentTableRepository.save(appointment);

            // Send notifications
            notificationService.sendNotification(
                    getCurrentUser().getId(),
                    "Specialist Consultation Requested",
                    "Your specialist consultation has been requested for " +
                            request.getConsultationDate() + " at " + request.getSlotStartTime() +
                            " and is awaiting confirmation.",
                    "SPECIALIST_CONSULTATION",
                    appointment.getId()
            );

            notificationService.sendNotification(
                    specialistConsultation.getDoctor().getUser().getId(),
                    "New Specialist Consultation Request",
                    "You have a new specialist consultation request scheduled for " +
                            request.getConsultationDate() + " at " + request.getSlotStartTime() +
                            ". Please confirm or cancel it.",
                    "SPECIALIST_CONSULTATION",
                    appointment.getId()
            );

            return success("Specialist consultation requested successfully", appointment);
        } catch (Exception e) {
            log.error("Error booking specialist consultation: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAvailableDoctors(SpecialistCategory specialistCategory) {
        try {
            List<Doctor> doctors;

            if (specialistCategory != null) {
                doctors = doctorRepository.findBySpecialistCategory(specialistCategory);
            } else {
                doctors = doctorRepository.findAll();
            }

            // Filter doctors who have at least one active specialist consultation
            List<Doctor> doctorsWithConsultations = doctors.stream()
                    .filter(doctor -> {
                        List<SpecialistConsultation> consultations = specialistConsultationRepository.findByDoctorAndStatus(
                                doctor, STATUS.ACTIVE);

                        // Check if any consultations are available
                        return consultations.stream()
                                .anyMatch(consultation -> consultation.getAvailability() == AVAILABILITY.AVAILABLE);
                    })
                    .collect(Collectors.toList());

            return success(
                    "Available doctors for specialist consultation retrieved successfully",
                    doctorsWithConsultations.stream().map(Doctor::toResponse).toList()
            );
        } catch (Exception e) {
            log.error("Error retrieving available doctors: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAvailableSlots(Long doctorId, LocalDate date) {
        try {
            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new ClientException("Doctor not found"));

            // Get day of week from date
            DAY day = DAY.valueOf(date.getDayOfWeek().toString());

            // Get all specialist consultations for this doctor and day
            List<SpecialistConsultation> consultations = specialistConsultationRepository.findByDoctorAndDayAndStatus(
                    doctor, day, STATUS.ACTIVE);

            if (consultations.isEmpty()) {
                return success("No available slots found for this date", new ArrayList<>());
            }

            List<SlotResponse> availableSlots = new ArrayList<>();

            for (SpecialistConsultation consultation : consultations) {
                // Only consider available consultations
                if (consultation.getAvailability() == AVAILABILITY.AVAILABLE) {
                    // Generate time slots based on start time, end time, and duration
                    List<SlotResponse> slots = generateTimeSlots(
                            consultation.getId(),
                            date,
                            consultation.getStartTime(),
                            consultation.getEndTime(),
                            consultation.getDuration());

                    // Filter out already booked slots
                    List<SlotResponse> unbookedSlots = filterBookedSlots(slots, consultation.getId(), date);

                    availableSlots.addAll(unbookedSlots);
                }
            }

            return success("Available slots retrieved successfully", availableSlots);
        } catch (Exception e) {
            log.error("Error retrieving available slots: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getSpecialistConsultationDetails(Long consultationId) {
        try {
            SpecialistConsultationAppointmentTable appointment = specialistConsultationAppointmentTableRepository.findById(consultationId)
                    .orElseThrow(() -> new ClientException("Specialist consultation appointment not found"));

            // Ensure the current user is either the patient or the doctor
            if (!getCurrentUser().getId().equals(appointment.getUser().getId()) &&
                    !getCurrentUser().getId().equals(appointment.getSpecialistConsultation().getDoctor().getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("You are not authorized to view this consultation");
            }

            return success("Specialist consultation details retrieved successfully", appointment);
        } catch (Exception e) {
            log.error("Error retrieving specialist consultation details: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getUserSpecialistConsultations() {
        try {
            List<SpecialistConsultationAppointmentTable> appointments =
                    specialistConsultationAppointmentTableRepository.findByUserIdOrderByConsultationDateDesc(getCurrentUser().getId());

            return success("User specialist consultations retrieved successfully", appointments);
        } catch (Exception e) {
            log.error("Error retrieving user specialist consultations: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getDoctorSpecialistConsultations() {
        try {
            // Get the doctor entity for the current user
            Doctor doctor = doctorRepository.findByUserId(getCurrentUser().getId())
                    .orElseThrow(() -> new ClientException("Doctor profile not found for current user"));

            // Get all specialist consultations for this doctor
            List<SpecialistConsultation> specialistConsultations = specialistConsultationRepository.findByDoctor(doctor);

            // Get all appointments for these consultations
            List<SpecialistConsultationAppointmentTable> allAppointments = new ArrayList<>();

            for (SpecialistConsultation consultation : specialistConsultations) {
                List<SpecialistConsultationAppointmentTable> appointments =
                        specialistConsultationAppointmentTableRepository.findBySpecialistConsultationOrderByConsultationDateDesc(consultation);
                allAppointments.addAll(appointments);
            }

            return success("Doctor specialist consultations retrieved successfully", allAppointments);
        } catch (Exception e) {
            log.error("Error retrieving doctor specialist consultations: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> cancelSpecialistConsultation(Long appointmentId) {
        try {
            SpecialistConsultationAppointmentTable appointment = specialistConsultationAppointmentTableRepository.findById(appointmentId)
                    .orElseThrow(() -> new ClientException("Specialist consultation appointment not found"));

            // Ensure the current user is either the patient or the doctor
            if (!getCurrentUser().getId().equals(appointment.getUser().getId()) &&
                    !getCurrentUser().getId().equals(appointment.getSpecialistConsultation().getDoctor().getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("You are not authorized to cancel this consultation");
            }

            // Check if the appointment is already completed or cancelled
            if (appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED) {
                throw new ClientException("Appointment is already cancelled");
            }

            // Update appointment status
            appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
            specialistConsultationAppointmentTableRepository.save(appointment);

            // Send notifications
            notificationService.sendNotification(
                    appointment.getUser().getId(),
                    "Specialist Consultation Cancelled",
                    "Your specialist consultation scheduled for " + appointment.getConsultationDate() +
                            " has been cancelled.",
                    "SPECIALIST_CONSULTATION",
                    appointment.getId()
            );

            notificationService.sendNotification(
                    appointment.getSpecialistConsultation().getDoctor().getUser().getId(),
                    "Specialist Consultation Cancelled",
                    "A specialist consultation scheduled for " + appointment.getConsultationDate() +
                            " has been cancelled.",
                    "SPECIALIST_CONSULTATION",
                    appointment.getId()
            );

            return success("Specialist consultation cancelled successfully", null);
        } catch (Exception e) {
            log.error("Error cancelling specialist consultation: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> completeSpecialistConsultation(Long appointmentId) {
        try {
            SpecialistConsultationAppointmentTable appointment = specialistConsultationAppointmentTableRepository.findById(appointmentId)
                    .orElseThrow(() -> new ClientException("Specialist consultation appointment not found"));

            // Ensure the current user is the doctor
            if (!getCurrentUser().getId().equals(appointment.getSpecialistConsultation().getDoctor().getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("Only the doctor can complete this consultation");
            }

            // Check if the appointment is already completed or cancelled
            if (appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED) {
                throw new ClientException("Cannot complete a cancelled appointment");
            }

            // Update appointment status
            appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
            specialistConsultationAppointmentTableRepository.save(appointment);

            // Send notification
            notificationService.sendNotification(
                    appointment.getUser().getId(),
                    "Specialist Consultation Completed",
                    "Your specialist consultation has been marked as completed.",
                    "SPECIALIST_CONSULTATION",
                    appointment.getId()
            );

            return success("Specialist consultation completed successfully", null);
        } catch (Exception e) {
            log.error("Error completing specialist consultation: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> toggleConsultationAvailability(Long consultationId) {
        try {
            SpecialistConsultation consultation = specialistConsultationRepository.findById(consultationId)
                    .orElseThrow(() -> new ClientException("Specialist consultation not found"));

            // Ensure the current user is the doctor or an admin
            if (!getCurrentUser().getId().equals(consultation.getDoctor().getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("You are not authorized to toggle this consultation's availability");
            }

            // Toggle availability
            if (consultation.getAvailability() == AVAILABILITY.AVAILABLE) {
                consultation.setAvailability(AVAILABILITY.UNAVAILABLE);
            } else {
                // If enabling specialist consultation, disable emergency consultation
                ResponseEntity<?> disableResponse = scheduleConflictService.disableEmergencyConsultationForSpecialist(consultation.getDoctor().getId());
                if (!disableResponse.getStatusCode().is2xxSuccessful()) {
                    return disableResponse;
                }

                consultation.setAvailability(AVAILABILITY.AVAILABLE);
            }

            specialistConsultationRepository.save(consultation);

            return success("Consultation availability toggled successfully", consultation);
        } catch (Exception e) {
            log.error("Error toggling consultation availability: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> confirmSpecialistConsultation(Long appointmentId) {
        try {
            SpecialistConsultationAppointmentTable appointment = specialistConsultationAppointmentTableRepository.findById(appointmentId)
                    .orElseThrow(() -> new ClientException("Specialist consultation appointment not found"));

            // Ensure the current user is the doctor
            if (!getCurrentUser().getId().equals(appointment.getSpecialistConsultation().getDoctor().getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("Only the doctor can confirm this consultation");
            }

            // Check if the appointment is in pending status
            if (appointment.getAppointmentStatus() != AppointmentStatus.PENDING) {
                throw new ClientException("Can only confirm appointments in pending status");
            }

            // Update appointment status
            appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
            specialistConsultationAppointmentTableRepository.save(appointment);

            // Send confirmation notification with meeting link
            notificationService.sendNotification(
                    appointment.getUser().getId(),
                    "Specialist Consultation Confirmed",
                    "Your specialist consultation scheduled for " + appointment.getConsultationDate() +
                            " has been confirmed. Please join using the meeting link: " + appointment.getMeetingLink(),
                    "SPECIALIST_CONSULTATION",
                    appointment.getId()
            );

            return success("Specialist consultation confirmed successfully", appointment);
        } catch (Exception e) {
            log.error("Error confirming specialist consultation: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void validateCreateSpecialistConsultationRequest(CreateSpecialistConsultationRequest request) {
        super.validateBody(request);

        if (request.getDoctor() == null) {
            throw new ClientException("Doctor ID is required");
        }

        if (request.getStatus() == null) {
            throw new ClientException("Status is required");
        }

        if (request.getAvailability() == null) {
            throw new ClientException("Availability is required");
        }

        if (request.getDate_day() == null) {
            throw new ClientException("Day is required");
        }

        if (request.getStartTime() == null) {
            throw new ClientException("Start time is required");
        }

        if (request.getEndTime() == null) {
            throw new ClientException("End time is required");
        }

        if (request.getDuration() == null || request.getDuration() <= 0) {
            throw new ClientException("Valid duration is required");
        }

        if (request.getFee() == null || request.getFee() <= 0) {
            throw new ClientException("Valid fee is required");
        }

        // Ensure end time is after start time
        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new ClientException("End time must be after start time");
        }

        // Ensure duration fits within the time range
        long minutesBetween = java.time.Duration.between(request.getStartTime(), request.getEndTime()).toMinutes();
        if (request.getDuration() > minutesBetween) {
            throw new ClientException("Duration is longer than the available time range");
        }
    }

    private void validateBookSpecialistConsultationRequest(SpecialistConsultationAppointmentRequest request) {
        super.validateBody(request);

        if (request.getSpecialistConsultationId() == null) {
            throw new ClientException("Specialist consultation ID is required");
        }

        if (request.getConsultationDate() == null) {
            throw new ClientException("Consultation date is required");
        }

        if (request.getSlotStartTime() == null) {
            throw new ClientException("Slot start time is required");
        }

        if (request.getSlotEndTime() == null) {
            throw new ClientException("Slot end time is required");
        }

        if (request.getIsBeneficiary() == null) {
            throw new ClientException("Beneficiary information is required");
        }

        if (request.getIsBeneficiary()) {
            if (isNullOrBlank(request.getBeneficiaryName())) {
                throw new ClientException("Beneficiary name is required");
            }

            if (isNullOrBlank(request.getBeneficiaryPhone())) {
                throw new ClientException("Beneficiary phone is required");
            }

            if (isNullOrBlank(request.getBeneficiaryAddress())) {
                throw new ClientException("Beneficiary address is required");
            }

            if (request.getBeneficiaryGender() == null) {
                throw new ClientException("Beneficiary gender is required");
            }

            if (request.getBeneficiaryAge() == null || request.getBeneficiaryAge() <= 0) {
                throw new ClientException("Valid beneficiary age is required");
            }
        }

        // Ensure the consultation date matches the day of week specified in the specialist consultation
        SpecialistConsultation consultation = specialistConsultationRepository.findById(request.getSpecialistConsultationId())
                .orElseThrow(() -> new ClientException("Specialist consultation not found"));

        DAY consultationDay = DAY.valueOf(request.getConsultationDate().getDayOfWeek().toString());
        if (consultation.getDay() != consultationDay) {
            throw new ClientException("Consultation date does not match the specified day of week");
        }

        // Ensure the consultation date is in the future
        if (request.getConsultationDate().isBefore(LocalDate.now())) {
            throw new ClientException("Consultation date must be in the future");
        }

        // Ensure the slot times are within the consultation time range
        if (request.getSlotStartTime().isBefore(consultation.getStartTime()) ||
                request.getSlotEndTime().isAfter(consultation.getEndTime())) {
            throw new ClientException("Slot times must be within the consultation time range");
        }
    }

    private List<SlotResponse> generateTimeSlots(Long consultationId, LocalDate date, LocalTime startTime, LocalTime endTime, int durationMinutes) {
        List<SlotResponse> slots = new ArrayList<>();

        LocalTime currentTime = startTime;
        while (currentTime.plusMinutes(durationMinutes).isBefore(endTime) ||
                currentTime.plusMinutes(durationMinutes).equals(endTime)) {
            LocalTime slotEnd = currentTime.plusMinutes(durationMinutes);

            slots.add(new SlotResponse(
                    consultationId,
                    date,
                    currentTime,
                    slotEnd,
                    durationMinutes,
                    true
            ));

            currentTime = slotEnd;
        }

        return slots;
    }

    private List<SlotResponse> filterBookedSlots(List<SlotResponse> slots, Long consultationId, LocalDate date) {
        // Get all booked appointments for this consultation and date
        List<SpecialistConsultationAppointmentTable> bookedAppointments =
                specialistConsultationAppointmentTableRepository.findBySpecialistConsultationIdAndConsultationDate(
                        consultationId, date);

        // Filter out slots that overlap with booked appointments
        return slots.stream()
                .filter(slot -> !isSlotOverlappingWithBookings(slot, bookedAppointments))
                .collect(Collectors.toList());
    }

    private boolean isSlotOverlappingWithBookings(SlotResponse slot, List<SpecialistConsultationAppointmentTable> bookedAppointments) {
        for (SpecialistConsultationAppointmentTable appointment : bookedAppointments) {
            if (appointment.getAppointmentStatus() != AppointmentStatus.CANCELLED &&
                    slot.getStartTime().equals(appointment.getConsultationSlotStart()) &&
                    slot.getEndTime().equals(appointment.getConsultationSlotEnd())) {
                return true;
            }
        }
        return false;
    }

    private boolean isSlotBooked(Long consultationId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return specialistConsultationAppointmentTableRepository.existsBySpecialistConsultationIdAndConsultationDateAndConsultationSlotStartAndConsultationSlotEndAndAppointmentStatusNot(
                consultationId, date, startTime, endTime, AppointmentStatus.CANCELLED);
    }

    private String generateMeetingLink() {
        // In a real application, this would integrate with Google Meet API
        // For now, just generate a random UUID-based link
        return "https://meet.google.com/" + UUID.randomUUID().toString().substring(0, 8);
    }
}*/
