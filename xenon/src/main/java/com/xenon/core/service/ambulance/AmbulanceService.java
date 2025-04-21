package com.xenon.core.service.ambulance;

import com.xenon.core.domain.request.ambulance.AmbulanceReviewRequest;
import com.xenon.core.domain.request.ambulance.CreateAmbulanceAccountRequest;
import com.xenon.data.entity.ambulance.AmbulanceType;
import org.springframework.http.ResponseEntity;

public interface AmbulanceService {
    ResponseEntity<?> createAmbulanceRequest(CreateAmbulanceAccountRequest body);

    ResponseEntity<?> createAmbulanceReviewRequest(AmbulanceReviewRequest body);

    ResponseEntity<?> getAmbulanceList(AmbulanceType type);

    ResponseEntity<?> getAmbulanceReviews(Long ambulanceId);
}
