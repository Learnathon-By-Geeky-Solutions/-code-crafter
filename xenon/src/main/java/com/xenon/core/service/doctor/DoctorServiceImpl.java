package com.xenon.core.service.doctor;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.request.doctor.CreateDoctorProfileRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl extends BaseService implements DoctorService {

    private final DoctorRepository doctorRepository;


    @Override
    public ResponseEntity<?> createDoctorProfileRequest(CreateDoctorProfileRequest body) {
        validateCreateDoctorAccountRequest(body);

        try {
            doctorRepository.save(body.toEntity(getCurrentUser()));
            return success("Doctor created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void validateCreateDoctorAccountRequest(CreateDoctorProfileRequest body) {
        super.validateBody(body);

        if (body.getDoctorTitle() == null) throw requiredField("Doctor Title");
        if (body.getDoctorType() == null) throw requiredField("Doctor Type");
        if (body.getSpecialistCategory() == null) throw requiredField("Specialist Category");
        if (body.getDateOfBirth() == null) throw requiredField("Date of Birth");
        if (isNullOrBlank(body.getNid()) && isNullOrBlank(body.getPassport())) throw requiredField("NID/Passport");
        if (isNullOrBlank(body.getRegistrationNo())) throw requiredField("Registration No.");
        if (body.getExperience() == null) throw requiredField("Experience");
        if (isNullOrBlank(body.getHospital())) throw requiredField("Hospital");
        if (isNullOrBlank(body.getAbout())) throw requiredField("About");
        if (isNullOrBlank(body.getAreaOfExpertise())) throw requiredField("Area Of Expertise");
        if (isNullOrBlank(body.getPatientCarePolicy())) throw requiredField("Patient Care Policy");
        if (isNullOrBlank(body.getEducation())) throw requiredField("Education");
        if (isNullOrBlank(body.getExperienceInfo())) throw requiredField("Experience Info");

        if (!isNullOrBlank(body.getNid()) && doctorRepository.existsByNid(body.getNid())) throw clientException("NID already exists!");
        if (!isNullOrBlank(body.getPassport()) && doctorRepository.existsByPassport(body.getPassport())) throw clientException("Passport already exists!");
        if (doctorRepository.existsByRegistrationNo(body.getRegistrationNo())) throw clientException("Registration number already exists!");

    }
}
