package com.xenon.core.service.bloodBank;

import com.xenon.core.domain.request.bloodBank.CreateBloodBankAccountRequest;
import org.springframework.http.ResponseEntity;

public interface BloodBankService {

    ResponseEntity<?> createBloodBankRequest(CreateBloodBankAccountRequest body);
}
