package com.xenon.core.service.blood;

import com.xenon.core.domain.request.blood.CreateBloodRequestPost;
import com.xenon.data.entity.donor.BloodType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface BloodRequestPostService {
    ResponseEntity<?> createBloodRequestPost(CreateBloodRequestPost body);

    ResponseEntity<?> getBloodDashboard();

    ResponseEntity<?> getBloodRequestPostPage(Pageable pageable);

    ResponseEntity<?> getBloodPostPage(Pageable pageable);

    ResponseEntity<?> getBloodRequestsByType(BloodType bloodType, Pageable pageable);

    ResponseEntity<?> getBloodRequestsByLocation(Long upazilaId, Pageable pageable);

    ResponseEntity<?> getBloodRequestsByTypeAndLocation(BloodType bloodType, Long upazilaId, Pageable pageable);

    ResponseEntity<?> getBloodRequestDetails(Long requestId);
}