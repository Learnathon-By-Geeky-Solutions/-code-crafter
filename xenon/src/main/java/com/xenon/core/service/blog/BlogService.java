package com.xenon.core.service.blog;

import com.xenon.core.domain.request.blog.BlogPostRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface BlogService {
    ResponseEntity<?> createBlogPostRequest(BlogPostRequest body);

    ResponseEntity<?> getAllBlogs(Pageable pageable);

    ResponseEntity<?> getBlogsByCategory(String category, Pageable pageable);

    ResponseEntity<?> getUserBlogs(Pageable pageable);

    ResponseEntity<?> updateBlog(Long id, BlogPostRequest body);

    ResponseEntity<?> deleteBlog(Long id);

    ResponseEntity<?> getBlogById(Long id);

    // New methods for enhanced functionality
    ResponseEntity<?> searchBlogs(String query, Pageable pageable);

    ResponseEntity<?> getTrendingBlogs(String trendingBy, Pageable pageable);

    // View count increment
    @Transactional
    ResponseEntity<?> incrementViewCount(Long id);
}