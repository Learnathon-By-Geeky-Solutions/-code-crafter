package com.xenon.core.service.blog.doctorArticle;

import com.xenon.core.domain.request.blog.BlogPostRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DoctorArticleService {
    ResponseEntity<?> createArticle(BlogPostRequest body);

    ResponseEntity<?> getAllArticles(Pageable pageable);


    ResponseEntity<?> updateArticle(Long id, BlogPostRequest articleDto);

    ResponseEntity<?> deleteArticle(Long id);
}
