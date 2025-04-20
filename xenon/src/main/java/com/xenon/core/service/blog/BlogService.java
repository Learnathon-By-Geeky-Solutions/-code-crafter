package com.xenon.core.service.blog;

import com.xenon.core.domain.request.blog.CreateBlogPostRequest;
import org.springframework.http.ResponseEntity;

public interface BlogService {
    ResponseEntity<?> createBlogPostRequest(CreateBlogPostRequest body);

    ResponseEntity<?> getBlogPosts();
}
