package com.xenon.core.service.donor;

import com.xenon.core.domain.request.donor.BloodGivenInfoRequest;
import com.xenon.core.domain.request.donor.CreateDonorAccountRequest;
import org.springframework.http.ResponseEntity;

public interface DonorService {
    ResponseEntity<?> createDonorAccountRequest(CreateDonorAccountRequest body);

    ResponseEntity<?> bloodGivenInfoRequest(BloodGivenInfoRequest body);
}
