package com.xenon.core.service.blog;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface LikeService {
    ResponseEntity<?> toggleLike(Long blogId);
    /*ResponseEntity<?> getLikeStatus(Long blogId);
    ResponseEntity<?> getLikesByBlogId(Long blogId);
    ResponseEntity<?> getLikesByBlogIdPaginated(Long blogId, Pageable pageable);
    ResponseEntity<?> getMostLikedBlogs(Pageable pageable);
    ResponseEntity<?> getMostLikedBlogsByCategory(String category, Pageable pageable);*/
    ResponseEntity<?> getUserLikedBlogs(Pageable pageable);
   /* ResponseEntity<?> countLikesByBlogId(Long blogId);*/
}