package com.xenon.core.service.blog.doctorArticle;

import com.xenon.core.domain.request.blog.BlogPostRequest;
import com.xenon.core.domain.request.blog.DoctorArticlePost;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DoctorArticleService {
    ResponseEntity<?> createArticle(DoctorArticlePost body);

    ResponseEntity<?> getAllArticles(Pageable pageable);

    ResponseEntity<?> searchArticles(String query, Pageable pageable);

    ResponseEntity<?> updateArticle(Long id, BlogPostRequest articleDto);

    ResponseEntity<?> deleteArticle(Long id);

    ResponseEntity<?> getArticleById(Long id);
}