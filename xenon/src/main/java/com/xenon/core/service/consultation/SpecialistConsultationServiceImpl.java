package com.xenon.core.service.consultation;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.request.consultation.CreateSpecialistConsultationAppointmentTableRequest;
import com.xenon.core.domain.request.consultation.CreateSpecialistConsultationRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.consultation.SpecialistConsultation;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.repository.DoctorRepository;
import com.xenon.data.repository.SpecialistConsultationAppointmentTableRepository;
import com.xenon.data.repository.SpecialistConsultationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpecialistConsultationServiceImpl extends BaseService implements SpecialistConsultationService {

    private final SpecialistConsultationRepository specialistConsultationRepository;
    private final DoctorRepository doctorRepository;
    private final SpecialistConsultationAppointmentTableRepository specialistConsultationAppointmentTableRepository;


    @Override
    public ResponseEntity<?> createSpecialistConsultationRequest(CreateSpecialistConsultationRequest body) {
        validateCreateSpecialistConsultationRequest(body);

        Doctor doctor = doctorRepository.findById(body.getDoctor()).orElseThrow(() -> clientException("Doctor not found"));

        try {
            specialistConsultationRepository.save(body.toEntity(doctor));
            return success("Specialist consultation created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> createSpecialistConsultationAppointmentTableRequest(CreateSpecialistConsultationAppointmentTableRequest body) {
        validateCreateSpecialistConsultationAppointmentTableRequest(body);

        SpecialistConsultation specialistConsultation = specialistConsultationRepository.findById(body.getSpecialistConsultation()).orElseThrow(() -> clientException("Specialist consultation not found"));

        try {
            specialistConsultationAppointmentTableRepository.save(body.toEntity(specialistConsultation, getCurrentUser()));
            return success("Specialist consultation appointment table created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void validateCreateSpecialistConsultationRequest(CreateSpecialistConsultationRequest body) {
        super.validateBody(body);

        if (body.getStatus() == null) throw requiredField("Status");
        if (body.getAvailability() == null) throw requiredField("Availability");
        if (body.getDate() == null) throw requiredField("Date");
        if (body.getStartTime() == null) throw requiredField("Start time");
        if (body.getEndTime() == null) throw requiredField("End time");
        if (body.getDuration() == null) throw requiredField("Duration");
        if (body.getFee() == null) throw requiredField("Fee");
    }

    private void validateCreateSpecialistConsultationAppointmentTableRequest(CreateSpecialistConsultationAppointmentTableRequest body) {
        super.validateBody(body);
    }
}
