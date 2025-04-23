package com.xenon.core.service.blood;

import com.xenon.core.domain.request.blood.CreateBloodRequestPost;
import org.springframework.http.ResponseEntity;

public interface BloodRequestPostService {
    ResponseEntity<?> createBloodRequestPost(CreateBloodRequestPost body);

    ResponseEntity<?> getBloodDashboard();

    ResponseEntity<?> getBloodRequestPostPage();

    ResponseEntity<?> getBloodPostPage();
}
