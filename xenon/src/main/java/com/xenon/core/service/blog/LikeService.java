package com.xenon.core.service.blog;

import org.springframework.http.ResponseEntity;

public interface LikeService {
    ResponseEntity<?> toggleLike(Long blogId);
    ResponseEntity<?> getLikeStatus(Long blogId);
    ResponseEntity<?> getLikesByBlogId(Long blogId);
}
