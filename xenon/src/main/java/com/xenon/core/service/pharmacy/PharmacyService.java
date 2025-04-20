package com.xenon.core.service.pharmacy;

import com.xenon.core.domain.request.pharmacy.CreatePharmacyAccountRequest;
import org.springframework.http.ResponseEntity;

public interface PharmacyService {
    ResponseEntity<?> createPharmacyRequest(CreatePharmacyAccountRequest body);
}
