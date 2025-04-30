package com.xenon.core.service.blood;

import com.xenon.core.domain.request.blood.CreateBloodCommentRequest;
import org.springframework.http.ResponseEntity;

public interface BloodCommentService {
    ResponseEntity<?> createBloodCommentRequest(CreateBloodCommentRequest body);

}
