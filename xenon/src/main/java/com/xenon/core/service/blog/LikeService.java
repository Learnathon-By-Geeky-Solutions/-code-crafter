package com.xenon.core.service.blog;

import com.xenon.core.domain.request.blog.CreateLikeRequest;
import org.springframework.http.ResponseEntity;

public interface LikeService {
    ResponseEntity<?> createLikeRequest(CreateLikeRequest body);
}
