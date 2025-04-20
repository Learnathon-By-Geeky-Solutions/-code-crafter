package com.xenon.core.service.blog;

import com.xenon.core.domain.request.blog.CreateCommentRequest;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<?> createCommentRequest(CreateCommentRequest body);
}
