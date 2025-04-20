package com.xenon.core.service.doctor;

import com.xenon.core.domain.request.doctor.CreateDoctorProfileRequest;
import org.springframework.http.ResponseEntity;

public interface DoctorService {

    ResponseEntity<?> createDoctorProfileRequest(CreateDoctorProfileRequest body);
}
