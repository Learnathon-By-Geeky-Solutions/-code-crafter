package com.xenon.core.service.blood;

import com.xenon.core.domain.request.blood.CreateBloodResponseRequest;
import org.springframework.http.ResponseEntity;

public interface BloodResponseService {
    ResponseEntity<?> createBloodResponseRequest(CreateBloodResponseRequest body);
}
