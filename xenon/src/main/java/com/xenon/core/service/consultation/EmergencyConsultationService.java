package com.xenon.core.service.consultation;

import com.xenon.core.domain.request.consultation.CreateEmergencyConsultationRequest;
import com.xenon.core.domain.request.consultation.CreateEmergencyConsultationTableRequest;
import org.springframework.http.ResponseEntity;

public interface EmergencyConsultationService {
    ResponseEntity<?> createEmergencyConsultationRequest(CreateEmergencyConsultationRequest body);

    ResponseEntity<?> createEmergencyConsultationTableRequest(CreateEmergencyConsultationTableRequest body);

    ResponseEntity<?> getSpecialistDoctors();
}
