package com.xenon.core.service.hospital.appointment;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.hospital.doctorBooking.OfflineAppointmentRequest;
import com.xenon.core.domain.response.consultation.SlotResponse;
import com.xenon.core.service.BaseService;
import com.xenon.core.service.notification.NotificationService;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.doctor.SpecialistCategory;
import com.xenon.data.entity.hospital.*;
import com.xenon.data.entity.hospital.offlineBooking.OfflineAppointmentTable;
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
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OfflineAppointmentServiceImpl extends BaseService implements OfflineAppointmentService {

    private final HospitalBranchRepository hospitalBranchRepository;
    private final DoctorRepository doctorRepository;
    private final OfflineDoctorAffiliationRepository offlineDoctorAffiliationRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final OfflineAppointmentTableRepository offlineAppointmentTableRepository;
    private final NotificationService notificationService;
    private final HospitalRepository hospitalRepository;

    @Override
    @Transactional
    public ResponseEntity<?> bookOfflineAppointment(OfflineAppointmentRequest request) {
        validateBookOfflineAppointmentRequest(request);

        try {
            DoctorSchedule schedule = doctorScheduleRepository.findById(request.getDoctorScheduleId())
                    .orElseThrow(() -> new ClientException("Doctor schedule not found"));

            // Check if the doctor is available
            if (schedule.getAvailability() != AVAILABILITY.AVAILABLE) {
                throw new ClientException("Doctor is not available for this schedule");
            }

            // Check if the appointment date is a valid day for the schedule
            DAY appointmentDay = DAY.valueOf(request.getAppointmentDate().getDayOfWeek().toString());
            if (schedule.getDay() != appointmentDay) {
                throw new ClientException("The selected date does not match the schedule day");
            }

            // Check if the time is within the schedule time range
            if (request.getAppointmentTime().isBefore(schedule.getStartTime()) ||
                    request.getAppointmentTime().isAfter(schedule.getEndTime().minusMinutes(schedule.getDuration()))) {
                throw new ClientException("The selected time is outside the schedule time range");
            }

            // Check if there are too many bookings for this time slot
            int existingBookings = offlineAppointmentTableRepository.countByDoctorScheduleAndAppointmentDateAndAppointmentTimeBetweenAndAppointmentStatusNot(
                    schedule,
                    request.getAppointmentDate(),
                    request.getAppointmentTime(),
                    request.getAppointmentTime().plusMinutes(schedule.getDuration()),
                    AppointmentStatus.CANCELLED);

            if (existingBookings >= schedule.getBooking_quantity()) {
                throw new ClientException("This time slot is already fully booked");
            }

            // Create appointment
            OfflineAppointmentTable appointment = request.toEntity(getCurrentUser(), schedule);
            offlineAppointmentTableRepository.save(appointment);

            // Send notifications
            notificationService.sendNotification(
                    getCurrentUser().getId(),
                    "Offline Appointment Booked",
                    "Your offline appointment has been booked for " +
                            request.getAppointmentDate() + " at " + request.getAppointmentTime() +
                            " with Dr. " + schedule.getOfflineDoctorAffiliation().getDoctor().getUser().getFirstName() +
                            " " + schedule.getOfflineDoctorAffiliation().getDoctor().getUser().getLastName() +
                            " at " + schedule.getOfflineDoctorAffiliation().getHospitalBranch().getBranchName(),
                    "OFFLINE_APPOINTMENT",
                    appointment.getId()
            );

            // Notify the doctor
            notificationService.sendNotification(
                    schedule.getOfflineDoctorAffiliation().getDoctor().getUser().getId(),
                    "New Offline Appointment",
                    "You have a new offline appointment scheduled for " +
                            request.getAppointmentDate() + " at " + request.getAppointmentTime() +
                            " at " + schedule.getOfflineDoctorAffiliation().getHospitalBranch().getBranchName(),
                    "OFFLINE_APPOINTMENT",
                    appointment.getId()
            );

            // Notify the hospital
            notificationService.sendNotification(
                    schedule.getOfflineDoctorAffiliation().getHospitalBranch().getHospital().getUser().getId(),
                    "New Offline Appointment",
                    "There is a new offline appointment scheduled for " +
                            request.getAppointmentDate() + " at " + request.getAppointmentTime() +
                            " with Dr. " + schedule.getOfflineDoctorAffiliation().getDoctor().getUser().getFirstName() +
                            " " + schedule.getOfflineDoctorAffiliation().getDoctor().getUser().getLastName(),
                    "OFFLINE_APPOINTMENT",
                    appointment.getId()
            );

            return success("Offline appointment booked successfully", appointment);
        } catch (Exception e) {
            log.error("Error booking offline appointment: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAvailableHospitals() {
        try {
            List<HospitalBranch> hospitalBranches = hospitalBranchRepository.findAll();
            return success("Hospital branches retrieved successfully", hospitalBranches);
        } catch (Exception e) {
            log.error("Error retrieving hospital branches: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getHospitalDepartments(Long hospitalBranchId) {
        try {
            HospitalBranch hospitalBranch = hospitalBranchRepository.findById(hospitalBranchId)
                    .orElseThrow(() -> new ClientException("Hospital branch not found"));

            // Get all doctors affiliated with this hospital branch
            List<OfflineDoctorAffiliation> affiliations = offlineDoctorAffiliationRepository.findByHospitalBranchId(hospitalBranchId);

            // Extract unique specialist categories
            List<SpecialistCategory> specialistCategories = affiliations.stream()
                    .map(affiliation -> affiliation.getDoctor().getSpecialistCategory())
                    .distinct()
                    .collect(Collectors.toList());

            return success("Hospital departments retrieved successfully", specialistCategories);
        } catch (Exception e) {
            log.error("Error retrieving hospital departments: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getDoctorsByDepartment(Long hospitalBranchId, SpecialistCategory specialistCategory) {
        try {
            HospitalBranch hospitalBranch = hospitalBranchRepository.findById(hospitalBranchId)
                    .orElseThrow(() -> new ClientException("Hospital branch not found"));

            // Get all doctors affiliated with this hospital branch and specialist category
            List<OfflineDoctorAffiliation> affiliations;
            if (specialistCategory != null) {
                affiliations = offlineDoctorAffiliationRepository.findByHospitalBranchIdAndDoctor_SpecialistCategory(
                        hospitalBranchId, specialistCategory);
            } else {
                affiliations = offlineDoctorAffiliationRepository.findByHospitalBranchId(hospitalBranchId);
            }

            // Extract doctor information with affiliation details
            List<Doctor> doctors = affiliations.stream()
                    .map(OfflineDoctorAffiliation::getDoctor)
                    .collect(Collectors.toList());

            return success("Doctors by department retrieved successfully", doctors.stream().map(Doctor::toResponse).toList());
        } catch (Exception e) {
            log.error("Error retrieving doctors by department: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getDoctorSchedules(Long hospitalBranchId, Long doctorId) {
        try {
            // Get doctor schedules for this hospital branch and doctor
            List<DoctorSchedule> schedules = doctorScheduleRepository.findByOfflineDoctorAffiliation_HospitalBranchIdAndOfflineDoctorAffiliation_DoctorId(
                    hospitalBranchId, doctorId);

            return success("Doctor schedules retrieved successfully", schedules);
        } catch (Exception e) {
            log.error("Error retrieving doctor schedules: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAvailableSlots(Long scheduleId, LocalDate date) {
        try {
            DoctorSchedule schedule = doctorScheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new ClientException("Doctor schedule not found"));

            // Check if the date matches the schedule day
            DAY dateDay = DAY.valueOf(date.getDayOfWeek().toString());
            if (schedule.getDay() != dateDay) {
                return success("No available slots for this date", new ArrayList<>());
            }

            // Generate time slots
            List<SlotResponse> slots = generateTimeSlots(
                    schedule.getId(),
                    date,
                    schedule.getStartTime(),
                    schedule.getEndTime(),
                    schedule.getDuration(),
                    schedule.getBooking_quantity());

            // Filter out slots that are fully booked
            List<SlotResponse> availableSlots = filterBookedSlots(slots, schedule, date);

            return success("Available slots retrieved successfully", availableSlots);
        } catch (Exception e) {
            log.error("Error retrieving available slots: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getUserOfflineAppointments() {
        try {
            List<OfflineAppointmentTable> appointments = offlineAppointmentTableRepository.findByUserIdOrderByAppointmentDateDescAppointmentTimeDesc(getCurrentUser().getId());

            return success("User offline appointments retrieved successfully", appointments);
        } catch (Exception e) {
            log.error("Error retrieving user offline appointments: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getHospitalOfflineAppointments(Long hospitalBranchId) {
        try {
            HospitalBranch hospitalBranch = hospitalBranchRepository.findById(hospitalBranchId)
                    .orElseThrow(() -> new ClientException("Hospital branch not found"));

            // Ensure the current user is associated with this hospital
            Hospital hospital = hospitalBranch.getHospital();
            if (!getCurrentUser().getId().equals(hospital.getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("You are not authorized to view appointments for this hospital");
            }

            List<OfflineAppointmentTable> appointments = offlineAppointmentTableRepository.findByDoctorSchedule_OfflineDoctorAffiliation_HospitalBranchIdOrderByAppointmentDateDescAppointmentTimeDesc(hospitalBranchId);

            return success("Hospital offline appointments retrieved successfully", appointments);
        } catch (Exception e) {
            log.error("Error retrieving hospital offline appointments: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getDoctorOfflineAppointments(Long doctorId) {
        try {
            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new ClientException("Doctor not found"));

            // Ensure the current user is this doctor
            if (!getCurrentUser().getId().equals(doctor.getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("You are not authorized to view appointments for this doctor");
            }

            List<OfflineAppointmentTable> appointments = offlineAppointmentTableRepository.findByDoctorSchedule_OfflineDoctorAffiliation_DoctorIdOrderByAppointmentDateDescAppointmentTimeDesc(doctorId);

            return success("Doctor offline appointments retrieved successfully", appointments);
        } catch (Exception e) {
            log.error("Error retrieving doctor offline appointments: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> cancelOfflineAppointment(Long appointmentId) {
        try {
            OfflineAppointmentTable appointment = offlineAppointmentTableRepository.findById(appointmentId)
                    .orElseThrow(() -> new ClientException("Offline appointment not found"));

            // Ensure the current user is authorized to cancel this appointment
            if (!getCurrentUser().getId().equals(appointment.getUser().getId()) &&
                    !getCurrentUser().getId().equals(appointment.getDoctorSchedule().getOfflineDoctorAffiliation().getDoctor().getUser().getId()) &&
                    !getCurrentUser().getId().equals(appointment.getDoctorSchedule().getOfflineDoctorAffiliation().getHospitalBranch().getHospital().getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("You are not authorized to cancel this appointment");
            }

            // Check if the appointment is already completed or cancelled
            if (appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED) {
                throw new ClientException("Appointment is already cancelled");
            }

            // Update appointment status
            appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
            offlineAppointmentTableRepository.save(appointment);

            // Send notifications
            notificationService.sendNotification(
                    appointment.getDoctorSchedule().getOfflineDoctorAffiliation().getDoctor().getUser().getId(),
                    "Offline Appointment Cancelled",
                    "An offline appointment scheduled for " + appointment.getAppointmentDate() +
                            " at " + appointment.getAppointmentTime() + " has been cancelled.",
                    "OFFLINE_APPOINTMENT",
                    appointment.getId()
            );

            notificationService.sendNotification(
                    appointment.getDoctorSchedule().getOfflineDoctorAffiliation().getHospitalBranch().getHospital().getUser().getId(),
                    "Offline Appointment Cancelled",
                    "An offline appointment scheduled for " + appointment.getAppointmentDate() +
                            " at " + appointment.getAppointmentTime() + " has been cancelled.",
                    "OFFLINE_APPOINTMENT",
                    appointment.getId()
            );

            return success("Offline appointment cancelled successfully", null);
        } catch (Exception e) {
            log.error("Error cancelling offline appointment: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> completeOfflineAppointment(Long appointmentId) {
        try {
            OfflineAppointmentTable appointment = offlineAppointmentTableRepository.findById(appointmentId)
                    .orElseThrow(() -> new ClientException("Offline appointment not found"));

            // Ensure the current user is authorized to complete this appointment
            if (!getCurrentUser().getId().equals(appointment.getDoctorSchedule().getOfflineDoctorAffiliation().getDoctor().getUser().getId()) &&
                    !getCurrentUser().getId().equals(appointment.getDoctorSchedule().getOfflineDoctorAffiliation().getHospitalBranch().getHospital().getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("You are not authorized to complete this appointment");
            }

            // Check if the appointment is already completed or cancelled
            if (appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED) {
                throw new ClientException("Cannot complete a cancelled appointment");
            }

            // Update appointment status
            appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
            offlineAppointmentTableRepository.save(appointment);

            // Send notification
            notificationService.sendNotification(
                    appointment.getUser().getId(),
                    "Offline Appointment Completed",
                    "Your offline appointment has been marked as completed.",
                    "OFFLINE_APPOINTMENT",
                    appointment.getId()
            );

            return success("Offline appointment completed successfully", null);
        } catch (Exception e) {
            log.error("Error completing offline appointment: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void validateBookOfflineAppointmentRequest(OfflineAppointmentRequest request) {
        super.validateBody(request);

        if (request.getDoctorScheduleId() == null) {
            throw new ClientException("Doctor schedule ID is required");
        }

        if (request.getAppointmentDate() == null) {
            throw new ClientException("Appointment date is required");
        }

        if (request.getAppointmentTime() == null) {
            throw new ClientException("Appointment time is required");
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

        // Ensure the appointment date is not in the past
        if (request.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new ClientException("Appointment date must be in the future");
        }

        // If appointment is today, ensure the time is in the future
        if (request.getAppointmentDate().isEqual(LocalDate.now()) &&
                request.getAppointmentTime().isBefore(LocalTime.now())) {
            throw new ClientException("Appointment time must be in the future");
        }
    }

    private List<SlotResponse> generateTimeSlots(
            Long scheduleId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            int durationMinutes,
            int maxBookingsPerSlot) {
        List<SlotResponse> slots = new ArrayList<>();

        LocalTime currentTime = startTime;
        while (currentTime.plusMinutes(durationMinutes).isBefore(endTime) ||
                currentTime.plusMinutes(durationMinutes).equals(endTime)) {
            LocalTime slotEnd = currentTime.plusMinutes(durationMinutes);

            slots.add(new SlotResponse(
                    scheduleId,
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

    private List<SlotResponse> filterBookedSlots(List<SlotResponse> slots, DoctorSchedule schedule, LocalDate date) {
        for (SlotResponse slot : slots) {
            // Count bookings for this time slot
            int bookings = offlineAppointmentTableRepository.countByDoctorScheduleAndAppointmentDateAndAppointmentTimeBetweenAndAppointmentStatusNot(
                    schedule,
                    date,
                    slot.getStartTime(),
                    slot.getEndTime(),
                    AppointmentStatus.CANCELLED);

            // Mark as unavailable if fully booked
            if (bookings >= schedule.getBooking_quantity()) {
                slot.setAvailable(false);
            }
        }

        return slots;
    }
}