package com.xenon.core.service.consultation.specialist;

import com.xenon.core.domain.request.consultation.specialist.CreateSpecialistConsultationRequest;
import com.xenon.core.domain.request.consultation.specialist.SpecialistConsultationAppointmentRequest;
import com.xenon.data.entity.doctor.SpecialistCategory;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface SpecialistConsultationService {

    ResponseEntity<?> createSpecialistConsultation(CreateSpecialistConsultationRequest request);

    ResponseEntity<?> bookSpecialistConsultation(SpecialistConsultationAppointmentRequest request);

    ResponseEntity<?> getAvailableDoctors(SpecialistCategory specialistCategory);

    ResponseEntity<?> getAvailableSlots(Long doctorId, LocalDate date);

    ResponseEntity<?> getSpecialistConsultationDetails(Long consultationId);

    ResponseEntity<?> getUserSpecialistConsultations();

    ResponseEntity<?> getDoctorSpecialistConsultations();

    ResponseEntity<?> cancelSpecialistConsultation(Long appointmentId);

    ResponseEntity<?> completeSpecialistConsultation(Long appointmentId);

    ResponseEntity<?> toggleConsultationAvailability(Long consultationId);
}