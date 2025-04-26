package com.xenon.core.service.bloodBank;

import com.xenon.core.domain.request.bloodBank.CreateBloodBankAccountRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface BloodBankService {
    ResponseEntity<?> createBloodBankRequest(CreateBloodBankAccountRequest body);

    ResponseEntity<?> getAllBloodBanks(Pageable pageable);

    ResponseEntity<?> getBloodBanksByLocation(Long upazilaId, Pageable pageable);

    ResponseEntity<?> getBloodBankDetails(Long bloodBankId);
}