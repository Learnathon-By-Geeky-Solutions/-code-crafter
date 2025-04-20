package com.xenon.core.service.consultation;


import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.request.consultation.CreateEmergencyConsultationRequest;
import com.xenon.core.domain.request.consultation.CreateEmergencyConsultationTableRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.consultation.EmergencyConsultation;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.hospital.AVAILABILITY;
import com.xenon.data.repository.DoctorRepository;
import com.xenon.data.repository.EmergencyConsultationRepository;
import com.xenon.data.repository.EmergencyConsultationTableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmergencyConsultationServiceImpl extends BaseService implements EmergencyConsultationService {

    private final EmergencyConsultationRepository emergencyConsultationRepository;
    private final DoctorRepository doctorRepository;
    private final EmergencyConsultationTableRepository emergencyConsultationTableRepository;

    @Override
    public ResponseEntity<?> createEmergencyConsultationRequest(CreateEmergencyConsultationRequest body) {
        validateCreateEmergencyConsultationRequest(body);

        Doctor doctor = doctorRepository.findById(body.getDoctor()).orElseThrow(() -> clientException("Doctor not found"));

        try {
            emergencyConsultationRepository.save(body.toEntity(doctor));
            return success("Emergency consultation created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }

    }

    @Override
    public ResponseEntity<?> createEmergencyConsultationTableRequest(CreateEmergencyConsultationTableRequest body) {
        validateCreateEmergencyConsultationTableRequest(body);

        EmergencyConsultation emergencyConsultation = emergencyConsultationRepository.findById(body.getEmergencyConsultation()).orElseThrow(() -> clientException("Emergency consultation not found"));

        try {
            emergencyConsultationTableRepository.save(body.toEntity(getCurrentUser(), emergencyConsultation));
            return success("Emergency consultation table created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getSpecialistDoctors() {
        return success(
                "Specialist doctors has been retrieved successfully",
                emergencyConsultationRepository.findAllByAvailability(AVAILABILITY.AVAILABLE).stream().map(EmergencyConsultation::toResponse).toList()
        );
    }

    private void validateCreateEmergencyConsultationRequest(CreateEmergencyConsultationRequest body) {
        super.validateBody(body);

        if (body.getAvailability() == null) throw requiredField("Availability");
        if (body.getFee() == null) throw requiredField("Fee");
    }

    private void validateCreateEmergencyConsultationTableRequest(CreateEmergencyConsultationTableRequest body) {
        super.validateBody(body);
    }
}
