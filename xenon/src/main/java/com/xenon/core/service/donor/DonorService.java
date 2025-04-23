package com.xenon.core.service.donor;

import com.xenon.core.domain.request.donor.BloodDonationInfoRequest;
import com.xenon.core.domain.request.donor.CreateDonorAccountRequest;
import org.springframework.http.ResponseEntity;

public interface DonorService {
    ResponseEntity<?> createDonorAccountRequest(CreateDonorAccountRequest body);

    ResponseEntity<?> bloodGivenInfoRequest(BloodDonationInfoRequest body);

    ResponseEntity<?> getDonationHistory();
}
