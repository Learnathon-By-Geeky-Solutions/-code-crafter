package com.xenon.core.service.blood;

import com.xenon.core.domain.request.DonorRequest;
import org.springframework.http.ResponseEntity;

public interface BloodService {
    ResponseEntity<?> createDonorRequest(DonorRequest body);
}
