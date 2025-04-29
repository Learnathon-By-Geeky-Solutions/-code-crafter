package com.xenon.core.service.pharmacy;

import com.xenon.core.domain.request.pharmacy.CreatePharmacyAccountRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PharmacyService {
    ResponseEntity<?> createPharmacyRequest(CreatePharmacyAccountRequest body);
    ResponseEntity<?> getPharmacyById(Long id);
    ResponseEntity<?> getAllPharmacies(Pageable pageable);
    ResponseEntity<?> searchPharmacies(String name, Pageable pageable);
    ResponseEntity<?> getPharmaciesByUpazilaId(Long upazilaId, Pageable pageable);
}