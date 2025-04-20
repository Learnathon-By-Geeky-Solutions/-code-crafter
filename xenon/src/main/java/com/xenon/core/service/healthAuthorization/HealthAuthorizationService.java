package com.xenon.core.service.healthAuthorization;

import com.xenon.core.domain.request.healthAuthorization.CreateAlertRequest;
import com.xenon.core.domain.request.healthAuthorization.CreateHealthAuthorizationAccountRequest;
import org.springframework.http.ResponseEntity;

public interface HealthAuthorizationService {
    ResponseEntity<?> createHealthAuthorizationRequest(CreateHealthAuthorizationAccountRequest body);

    ResponseEntity<?> createAlertRequest(CreateAlertRequest body);
}
