package com.xenon.core.service.consultation.emergency;

import com.xenon.core.domain.request.consultation.emergency.CreateEmergencyConsultationRequest;
import com.xenon.core.domain.request.consultation.emergency.EmergencyConsultationAppointmentRequest;
import com.xenon.data.entity.doctor.SpecialistCategory;
import org.springframework.http.ResponseEntity;

public interface EmergencyConsultationService {

    ResponseEntity<?> createEmergencyConsultation(CreateEmergencyConsultationRequest request);

    ResponseEntity<?> bookEmergencyConsultation(EmergencyConsultationAppointmentRequest request);

    ResponseEntity<?> getAvailableDoctors(SpecialistCategory specialistCategory);

    ResponseEntity<?> toggleDoctorAvailability(Long doctorId);

    ResponseEntity<?> getEmergencyConsultationDetails(Long consultationId);

    ResponseEntity<?> getUserEmergencyConsultations();

    ResponseEntity<?> getDoctorEmergencyConsultations();

    ResponseEntity<?> cancelEmergencyConsultation(Long appointmentId);

    ResponseEntity<?> completeEmergencyConsultation(Long appointmentId);

    /**
     * Confirms a pending emergency consultation appointment
     * @param appointmentId The appointment ID
     * @return ResponseEntity with success/failure message
     */
    ResponseEntity<?> confirmEmergencyConsultation(Long appointmentId);
}