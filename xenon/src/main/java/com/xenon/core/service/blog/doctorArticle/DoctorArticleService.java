package com.xenon.core.service.blog.doctorArticle;

import com.xenon.core.domain.request.blog.BlogPostRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DoctorArticleService {
    ResponseEntity<?> createArticle(BlogPostRequest body);

    ResponseEntity<?> createArticleWithCategory(BlogPostRequest body, String doctorCategory);

    ResponseEntity<?> getAllArticles(Pageable pageable);

    ResponseEntity<?> getArticlesByDoctorCategory(String doctorCategory, Pageable pageable);

    ResponseEntity<?> getFeaturedArticles(Pageable pageable);

    ResponseEntity<?> getFeaturedArticlesByDoctorCategory(String doctorCategory, Pageable pageable);

    ResponseEntity<?> getTrendingArticles(String trendingBy, Pageable pageable);

    ResponseEntity<?> searchArticles(String query, Pageable pageable);

    ResponseEntity<?> searchArticlesByDoctorCategory(String query, String doctorCategory, Pageable pageable);

    ResponseEntity<?> updateArticle(Long id, BlogPostRequest articleDto);

    ResponseEntity<?> updateArticleWithCategory(Long id, BlogPostRequest articleDto, String doctorCategory);

    ResponseEntity<?> deleteArticle(Long id);

    ResponseEntity<?> getArticleById(Long id);
}