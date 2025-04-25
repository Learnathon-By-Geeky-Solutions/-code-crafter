package com.xenon.core.service.doctor;

import com.xenon.core.domain.request.doctor.DoctorProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface DoctorService {

    @Transactional
    ResponseEntity<?> createDoctorProfileRequest(DoctorProfileRequest body);
    ResponseEntity<?> getDoctorProfile(Long doctorId);
    @Transactional
    ResponseEntity<?> updateDoctorProfile(Long doctorId, DoctorProfileRequest body);
}
