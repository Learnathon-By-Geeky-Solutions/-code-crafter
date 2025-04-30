package com.xenon.core.service.donor;

import com.xenon.core.domain.request.donor.BloodDonationInfoRequest;
import com.xenon.core.domain.request.donor.CreateDonorAccountRequest;
import com.xenon.core.domain.request.donor.UpdateDonorInterestRequest;
import com.xenon.core.domain.response.PageResponseRequest;
import com.xenon.data.entity.donor.BloodType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DonorService {
    ResponseEntity<?> createDonorAccountRequest(CreateDonorAccountRequest body);

    ResponseEntity<?> bloodGivenInfoRequest(BloodDonationInfoRequest body);

    ResponseEntity<?> getDonationHistory();

    ResponseEntity<?> updateDonorInterest(UpdateDonorInterestRequest body);

    ResponseEntity<?> getDonorProfile();

    ResponseEntity<?> getAvailableDonors(BloodType bloodType, Long upazilaId, Pageable pageable);
}