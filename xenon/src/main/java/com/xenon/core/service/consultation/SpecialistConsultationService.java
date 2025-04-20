package com.xenon.core.service.consultation;

import com.xenon.core.domain.request.consultation.CreateSpecialistConsultationAppointmentTableRequest;
import com.xenon.core.domain.request.consultation.CreateSpecialistConsultationRequest;
import org.springframework.http.ResponseEntity;

public interface SpecialistConsultationService {
    ResponseEntity<?> createSpecialistConsultationRequest(CreateSpecialistConsultationRequest body);

    ResponseEntity<?> createSpecialistConsultationAppointmentTableRequest(CreateSpecialistConsultationAppointmentTableRequest body);
}
