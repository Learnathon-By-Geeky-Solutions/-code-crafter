package com.xenon.core.service.ambulance;

import com.xenon.core.domain.request.ambulance.AmbulanceReviewRequest;
import com.xenon.core.domain.request.ambulance.CreateAmbulanceAccountRequest;
import com.xenon.data.entity.ambulance.AmbulanceStatus;
import com.xenon.data.entity.ambulance.AmbulanceType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AmbulanceService {
    ResponseEntity<?> createAmbulanceRequest(CreateAmbulanceAccountRequest body);

    ResponseEntity<?> createAmbulanceReviewRequest(AmbulanceReviewRequest body);

    ResponseEntity<?> getAmbulanceList(AmbulanceType type, Pageable pageable);

    ResponseEntity<?> getAmbulanceById(Long ambulanceId);

    ResponseEntity<?> getAmbulancesByArea(AmbulanceType type, String area, Pageable pageable);

    ResponseEntity<?> getAmbulanceReviews(Long ambulanceId, Pageable pageable);

    ResponseEntity<?> updateAmbulanceStatus(Long ambulanceId, AmbulanceStatus status);

    ResponseEntity<?> checkUserCanReview(Long ambulanceId);
}