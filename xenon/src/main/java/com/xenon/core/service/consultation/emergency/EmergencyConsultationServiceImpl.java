/*
package com.xenon.core.service.consultation.emergency;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.consultation.emergency.CreateEmergencyConsultationRequest;
import com.xenon.core.domain.request.consultation.emergency.EmergencyConsultationAppointmentRequest;
import com.xenon.core.domain.response.consultation.emergencyConsultation.EmergencyConsultationResponse;
import com.xenon.core.service.common.BaseService;
import com.xenon.core.service.notification.NotificationService;
import com.xenon.core.service.scheduling.ScheduleConflictService;
import com.xenon.data.entity.consultation.emergency.EmergencyConsultation;
import com.xenon.data.entity.consultation.emergency.EmergencyConsultationAppointmentTable;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.doctor.SpecialistCategory;
import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.hospital.AVAILABILITY;
import com.xenon.data.repository.DoctorRepository;
import com.xenon.data.repository.EmergencyConsultationRepository;
import com.xenon.data.repository.EmergencyConsultationTableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmergencyConsultationServiceImpl extends BaseService implements EmergencyConsultationService {

    private final EmergencyConsultationRepository emergencyConsultationRepository;
    private final EmergencyConsultationTableRepository emergencyConsultationTableRepository;
    private final DoctorRepository doctorRepository;
    private final NotificationService notificationService;
    private final ScheduleConflictService scheduleConflictService;

    @Override
    @Transactional
    public ResponseEntity<?> createEmergencyConsultation(CreateEmergencyConsultationRequest request) {
        validateCreateEmergencyConsultationRequest(request);

        try {
            Doctor doctor = doctorRepository.findById(request.getDoctorId())
                    .orElseThrow(() -> new ClientException("Doctor not found"));

            // Check if the doctor already has an emergency consultation
            if (emergencyConsultationRepository.existsByDoctor(doctor)) {
                throw new ClientException("Doctor already has an emergency consultation service");
            }

            // If the doctor wants to enable emergency consultation, disable specialist consultations
            if (request.getAvailability() == AVAILABILITY.AVAILABLE) {
                ResponseEntity<?> disableResponse = scheduleConflictService.disableSpecialistConsultationsForEmergency(doctor.getId());
                if (!disableResponse.getStatusCode().is2xxSuccessful()) {
                    return disableResponse;
                }
            }

            EmergencyConsultation emergencyConsultation = request.toEntity(doctor);
            emergencyConsultationRepository.save(emergencyConsultation);

            return success("Emergency consultation created successfully", mapToResponse(emergencyConsultation));
        } catch (Exception e) {
            log.error("Error creating emergency consultation: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> bookEmergencyConsultation(EmergencyConsultationAppointmentRequest request) {
        validateBookEmergencyConsultationRequest(request);

        try {
            EmergencyConsultation emergencyConsultation = emergencyConsultationRepository.findById(request.getEmergencyConsultationId())
                    .orElseThrow(() -> new ClientException("Emergency consultation not found"));

            // Check if the doctor is available
            if (emergencyConsultation.getAvailability() != AVAILABILITY.AVAILABLE) {
                throw new ClientException("Doctor is not available for emergency consultation");
            }

            // Create appointment
            EmergencyConsultationAppointmentTable appointment = request.toEntity(getCurrentUser(), emergencyConsultation);
            appointment.setConsultationDate(LocalDateTime.now());
            appointment.setAppointmentStatus(AppointmentStatus.PENDING); // Set as pending initially

            // Generate meeting link
            String meetingLink = generateMeetingLink();
            appointment.setMeetingLink(meetingLink);

            emergencyConsultationTableRepository.save(appointment);

            // Set doctor as unavailable temporarily until appointment is confirmed
            emergencyConsultation.setAvailability(AVAILABILITY.UNAVAILABLE);
            emergencyConsultationRepository.save(emergencyConsultation);

            // Send notifications
            notificationService.sendNotification(
                    getCurrentUser().getId(),
                    "Emergency Consultation Requested",
                    "Your emergency consultation request has been submitted and is awaiting confirmation.",
                    "EMERGENCY_CONSULTATION",
                    appointment.getId()
            );

            notificationService.sendNotification(
                    emergencyConsultation.getDoctor().getUser().getId(),
                    "New Emergency Consultation Request",
                    "You have a new emergency consultation request. Please confirm or cancel it soon.",
                    "EMERGENCY_CONSULTATION",
                    appointment.getId()
            );

            return success("Emergency consultation requested successfully", appointment);
        } catch (Exception e) {
            log.error("Error booking emergency consultation: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAvailableDoctors(SpecialistCategory specialistCategory) {
        try {
            List<EmergencyConsultation> availableDoctors;

            if (specialistCategory != null) {
                availableDoctors = emergencyConsultationRepository.findByAvailabilityAndDoctor_SpecialistCategory(
                        AVAILABILITY.AVAILABLE, specialistCategory);
            } else {
                availableDoctors = emergencyConsultationRepository.findByAvailability(AVAILABILITY.AVAILABLE);
            }

            List<EmergencyConsultationResponse> responses = availableDoctors.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());

            return success(
                    "Available doctors for emergency consultation retrieved successfully",
                    responses
            );
        } catch (Exception e) {
            log.error("Error retrieving available doctors: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> toggleDoctorAvailability(Long doctorId) {
        try {
            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new ClientException("Doctor not found"));

            // Ensure the current user is the doctor or an admin
            if (!getCurrentUser().getId().equals(doctor.getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("You are not authorized to toggle this doctor's availability");
            }

            EmergencyConsultation emergencyConsultation = emergencyConsultationRepository.findByDoctor(doctor)
                    .orElseThrow(() -> new ClientException("Emergency consultation not found for this doctor"));

            // Toggle availability
            if (emergencyConsultation.getAvailability() == AVAILABILITY.AVAILABLE) {
                emergencyConsultation.setAvailability(AVAILABILITY.UNAVAILABLE);
            } else {
                // Check if there are any pending appointments
                List<EmergencyConsultationAppointmentTable> pendingAppointments =
                        emergencyConsultationTableRepository.findByEmergencyConsultationAndAppointmentStatus(
                                emergencyConsultation, AppointmentStatus.PENDING);

                if (!pendingAppointments.isEmpty()) {
                    throw new ClientException("Cannot set availability to AVAILABLE while there are pending appointments");
                }

                // If enabling emergency consultation, disable specialist consultations
                ResponseEntity<?> disableResponse = scheduleConflictService.disableSpecialistConsultationsForEmergency(doctor.getId());
                if (!disableResponse.getStatusCode().is2xxSuccessful()) {
                    return disableResponse;
                }

                emergencyConsultation.setAvailability(AVAILABILITY.AVAILABLE);
            }

            emergencyConsultationRepository.save(emergencyConsultation);

            return success(
                    "Doctor availability toggled successfully",
                    mapToResponse(emergencyConsultation)
            );
        } catch (Exception e) {
            log.error("Error toggling doctor availability: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getEmergencyConsultationDetails(Long consultationId) {
        try {
            EmergencyConsultationAppointmentTable appointment = emergencyConsultationTableRepository.findById(consultationId)
                    .orElseThrow(() -> new ClientException("Emergency consultation appointment not found"));

            // Ensure the current user is either the patient or the doctor
            if (!getCurrentUser().getId().equals(appointment.getUser().getId()) &&
                    !getCurrentUser().getId().equals(appointment.getEmergencyConsultation().getDoctor().getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("You are not authorized to view this consultation");
            }

            return success("Emergency consultation details retrieved successfully", appointment);
        } catch (Exception e) {
            log.error("Error retrieving emergency consultation details: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getUserEmergencyConsultations() {
        try {
            List<EmergencyConsultationAppointmentTable> appointments =
                    emergencyConsultationTableRepository.findByUserIdOrderByConsultationDateDesc(getCurrentUser().getId());

            return success("User emergency consultations retrieved successfully", appointments);
        } catch (Exception e) {
            log.error("Error retrieving user emergency consultations: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getDoctorEmergencyConsultations() {
        try {
            // Get the doctor entity for the current user
            Doctor doctor = doctorRepository.findByUserId(getCurrentUser().getId())
                    .orElseThrow(() -> new ClientException("Doctor profile not found for current user"));

            // Get the emergency consultation entity
            EmergencyConsultation emergencyConsultation = emergencyConsultationRepository.findByDoctor(doctor)
                    .orElseThrow(() -> new ClientException("Emergency consultation not found for this doctor"));

            // Get all appointments for this emergency consultation
            List<EmergencyConsultationAppointmentTable> appointments =
                    emergencyConsultationTableRepository.findByEmergencyConsultationOrderByConsultationDateDesc(emergencyConsultation);

            return success("Doctor emergency consultations retrieved successfully", appointments);
        } catch (Exception e) {
            log.error("Error retrieving doctor emergency consultations: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> cancelEmergencyConsultation(Long appointmentId) {
        try {
            EmergencyConsultationAppointmentTable appointment = emergencyConsultationTableRepository.findById(appointmentId)
                    .orElseThrow(() -> new ClientException("Emergency consultation appointment not found"));

            // Ensure the current user is either the patient or the doctor
            if (!getCurrentUser().getId().equals(appointment.getUser().getId()) &&
                    !getCurrentUser().getId().equals(appointment.getEmergencyConsultation().getDoctor().getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("You are not authorized to cancel this consultation");
            }

            // Check if the appointment is already completed or cancelled
            if (appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED) {
                throw new ClientException("Appointment is already cancelled");
            }

            // Update appointment status
            appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
            emergencyConsultationTableRepository.save(appointment);

            // Make the doctor available again
            EmergencyConsultation emergencyConsultation = appointment.getEmergencyConsultation();
            emergencyConsultation.setAvailability(AVAILABILITY.AVAILABLE);
            emergencyConsultationRepository.save(emergencyConsultation);

            // Send notifications
            notificationService.sendNotification(
                    appointment.getUser().getId(),
                    "Emergency Consultation Cancelled",
                    "Your emergency consultation has been cancelled.",
                    "EMERGENCY_CONSULTATION",
                    appointment.getId()
            );

            notificationService.sendNotification(
                    emergencyConsultation.getDoctor().getUser().getId(),
                    "Emergency Consultation Cancelled",
                    "An emergency consultation has been cancelled.",
                    "EMERGENCY_CONSULTATION",
                    appointment.getId()
            );

            return success("Emergency consultation cancelled successfully", null);
        } catch (Exception e) {
            log.error("Error cancelling emergency consultation: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> completeEmergencyConsultation(Long appointmentId) {
        try {
            EmergencyConsultationAppointmentTable appointment = emergencyConsultationTableRepository.findById(appointmentId)
                    .orElseThrow(() -> new ClientException("Emergency consultation appointment not found"));

            // Ensure the current user is the doctor
            if (!getCurrentUser().getId().equals(appointment.getEmergencyConsultation().getDoctor().getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("Only the doctor can complete this consultation");
            }

            // Check if the appointment is already completed or cancelled
            if (appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED) {
                throw new ClientException("Cannot complete a cancelled appointment");
            }

            // Update appointment status
            appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
            emergencyConsultationTableRepository.save(appointment);

            // Make the doctor available again
            EmergencyConsultation emergencyConsultation = appointment.getEmergencyConsultation();
            emergencyConsultation.setAvailability(AVAILABILITY.AVAILABLE);
            emergencyConsultationRepository.save(emergencyConsultation);

            // Send notifications
            notificationService.sendNotification(
                    appointment.getUser().getId(),
                    "Emergency Consultation Completed",
                    "Your emergency consultation has been marked as completed.",
                    "EMERGENCY_CONSULTATION",
                    appointment.getId()
            );

            return success("Emergency consultation completed successfully", null);
        } catch (Exception e) {
            log.error("Error completing emergency consultation: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> confirmEmergencyConsultation(Long appointmentId) {
        try {
            EmergencyConsultationAppointmentTable appointment = emergencyConsultationTableRepository.findById(appointmentId)
                    .orElseThrow(() -> new ClientException("Emergency consultation appointment not found"));

            // Ensure the current user is the doctor
            if (!getCurrentUser().getId().equals(appointment.getEmergencyConsultation().getDoctor().getUser().getId()) &&
                    !getCurrentUser().getRole().equals(com.xenon.data.entity.user.UserRole.ADMIN)) {
                throw new ClientException("Only the doctor can confirm this consultation");
            }

            // Check if the appointment is in pending status
            if (appointment.getAppointmentStatus() != AppointmentStatus.PENDING) {
                throw new ClientException("Can only confirm appointments in pending status");
            }

            // Update appointment status
            appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
            emergencyConsultationTableRepository.save(appointment);

            // Send confirmation notification with meeting link
            notificationService.sendNotification(
                    appointment.getUser().getId(),
                    "Emergency Consultation Confirmed",
                    "Your emergency consultation has been confirmed. Please join using the meeting link: " + appointment.getMeetingLink(),
                    "EMERGENCY_CONSULTATION",
                    appointment.getId()
            );

            return success("Emergency consultation confirmed successfully", appointment);
        } catch (Exception e) {
            log.error("Error confirming emergency consultation: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void validateCreateEmergencyConsultationRequest(CreateEmergencyConsultationRequest request) {
        super.validateBody(request);

        if (request.getDoctorId() == null) {
            throw new ClientException("Doctor ID is required");
        }

        if (request.getAvailability() == null) {
            throw new ClientException("Availability is required");
        }

        if (request.getFee() == null || request.getFee() <= 0) {
            throw new ClientException("Valid fee is required");
        }
    }

    private void validateBookEmergencyConsultationRequest(EmergencyConsultationAppointmentRequest request) {
        super.validateBody(request);

        if (request.getEmergencyConsultationId() == null) {
            throw new ClientException("Emergency consultation ID is required");
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
    }

    private String generateMeetingLink() {
        // In a real application, this would integrate with Google Meet API
        // For now, just generate a random UUID-based link
        return "https://meet.google.com/" + UUID.randomUUID().toString().substring(0, 8);
    }

    private EmergencyConsultationResponse mapToResponse(EmergencyConsultation consultation) {
        if (consultation == null) {
            return null;
        }

        return new EmergencyConsultationResponse(
                consultation.getId(),
                consultation.getDoctor().getId(),
                consultation.getDoctor().getUser().getFirstName() + " " + consultation.getDoctor().getUser().getLastName(),
                consultation.getDoctor().getSpecialistCategory().toString(),
                consultation.getAvailability(),
                consultation.getFee()
        );
    }
}*/
