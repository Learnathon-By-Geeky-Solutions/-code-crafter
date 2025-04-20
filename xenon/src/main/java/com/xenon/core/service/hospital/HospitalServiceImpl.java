package com.xenon.core.service.hospital;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.hospital.*;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.hospital.*;
import com.xenon.data.entity.location.Upazila;
import com.xenon.data.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class HospitalServiceImpl extends BaseService implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalBranchRepository hospitalBranchRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final OfflineDoctorAffiliationRepository offlineDoctorAffiliationRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final OfflineAppointmentTableRepository offlineAppointmentTableRepository;



    @Override
    public ResponseEntity<?> CreateHospitalAccountRequest(CreateHospitalAccountRequest body) {
        validateCreateHospitalAccountRequest(body);

        try {
            hospitalRepository.save(body.toEntity(getCurrentUser()));
            return success("Hospital created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> createHospitalBranchAccountRequest(CreateHospitalBranchAccountRequest body) {
        validateCreateHospitalBranchAccountRequest(body);

        Hospital hospital = hospitalRepository.findById(body.getHospital()).orElseThrow(() -> clientException("Hospital not found"));
        Upazila upazila = upazilaRepository.findById(body.getUpazila()).orElseThrow(() -> clientException("Upazila not found"));

        try {
            hospitalBranchRepository.save(body.toEntity(hospital, upazila));
            return success("Hospital branch created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> createOfflineDoctorAffiliationRequest(CreateOfflineDoctorAffiliationRequest body) {

        validateCreateDoctorAffiliationRequest(body);

        Doctor doctor = doctorRepository.findById(body.getDoctor()).orElseThrow(() -> clientException("Doctor not found"));
        HospitalBranch hospitalBranch = hospitalBranchRepository.findById(body.getHospitalBranch()).orElseThrow(() -> clientException("Hospital branch not found"));
        try {
            offlineDoctorAffiliationRepository.save(body.toEntity(doctor, hospitalBranch));
            return success("Affiliation created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> createDoctorScheduleRequest(CreateDoctorScheduleRequest body) {

        validateCreateDoctorScheduleRequest(body);

        DoctorAffiliationId id = new DoctorAffiliationId(
                body.getDoctorId(),
                body.getHospitalBranchId()
        );

        OfflineDoctorAffiliation affiliation = offlineDoctorAffiliationRepository.findById(id)
                .orElseThrow(() -> new ClientException("Affiliation not found!"));

        try {
            doctorScheduleRepository.save(body.toEntity(affiliation));
            return success("Schedule created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }

    }

    @Override
    public ResponseEntity<?> createOfflineAppointmentTable(CreateOfflineAppointmentTable body) {
        validateCreateOfflineAppointmentTable(body);

        DoctorSchedule doctorSchedule = doctorScheduleRepository.findById(body.getDoctorScheduleId()).orElseThrow(() -> new ClientException("Schedule not found!"));

        try {
            offlineAppointmentTableRepository.save(body.toEntity(getCurrentUser(), doctorSchedule ));
            return success("Schedule created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }


    private void validateCreateHospitalAccountRequest(CreateHospitalAccountRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getRegistrationNo())) throw requiredField("Registration No.");
        if (body.getHospitalType() == null) throw requiredField("Hospital Type");

        if (hospitalRepository.existsByRegistrationNo(body.getRegistrationNo())) throw clientException("Registration number already exists!");
    }

    private void validateCreateHospitalBranchAccountRequest(CreateHospitalBranchAccountRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getBranchName())) throw requiredField("Branch name");
        if (isNullOrBlank(body.getAddress())) throw requiredField("Address");
        if (isNullOrBlank(body.getPhone())) throw requiredField("Contact info");
        if (isNullOrBlank(body.getEmail())) throw requiredField("Email");
        if (!PHONE_PATTERN.matcher(body.getPhone()).matches()) throw clientException("Invalid phone number");
        if (!EMAIL_PATTERN.matcher(body.getEmail()).matches()) throw clientException("Invalid email");
        if (body.getDoctor() == null) throw requiredField("Doctor");
        if (body.getBed() == null) throw requiredField("Bed");
        if (body.getEstablish_date() == null) throw requiredField("Establish date");

        if (hospitalBranchRepository.existsByEmail(body.getEmail())) throw clientException("Email already exists!");
        if (userRepository.existsByEmail(body.getEmail())) throw clientException("Email already exists!");
        if (hospitalBranchRepository.existsByPhone(body.getPhone())) throw clientException("Phone number already exists!");
        if (userRepository.existsByPhone(body.getPhone())) throw clientException("Phone number already exists!");
    }

    private void validateCreateDoctorAffiliationRequest(CreateOfflineDoctorAffiliationRequest body) {
        super.validateBody(body);

       if (body.getFee() == null) throw requiredField("Fee");

       if (offlineDoctorAffiliationRepository.existsByDoctorIdAndHospitalBranchId(body.getDoctor(), body.getHospitalBranch())) throw clientException("Affiliation already exists!");
    }

    private void validateCreateDoctorScheduleRequest(CreateDoctorScheduleRequest body) {
        super.validateBody(body);

        if (body.getDay() == null) throw requiredField("Day");
        if (body.getStartTime() == null) throw requiredField("Start time");
        if (body.getEndTime() == null) throw requiredField("End time");
        if (body.getAvailability() == null) throw requiredField("Availability");
        if (body.getBooking_quantity() == null) throw requiredField("Booking quantity");
        if (body.getDuration() == null) throw requiredField("Duration");
    }

    private void validateCreateOfflineAppointmentTable(CreateOfflineAppointmentTable body) {
        super.validateBody(body);
    }
}
