package com.xenon.core.service.blog.doctorArticle;

import com.xenon.core.domain.request.blog.BlogPostRequest;
import com.xenon.data.entity.blog.doctorArticle.DoctorArticleCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DoctorArticleService {
    ResponseEntity<?> createArticle(BlogPostRequest body, DoctorArticleCategory doctorCategory);

//    ResponseEntity<?> createArticleWithCategory(BlogPostRequest body, DoctorArticleCategory doctorCategory);

    ResponseEntity<?> getAllArticles(Pageable pageable);

    ResponseEntity<?> getArticlesByDoctorCategory(String doctorCategory, Pageable pageable);

//    ResponseEntity<?> getTrendingArticles(String trendingBy, Pageable pageable);

    ResponseEntity<?> searchArticles(String query, Pageable pageable);


    ResponseEntity<?> updateArticle(Long id, BlogPostRequest articleDto);

    ResponseEntity<?> updateArticleWithCategory(Long id, BlogPostRequest articleDto, DoctorArticleCategory doctorCategory);

    ResponseEntity<?> deleteArticle(Long id);

    ResponseEntity<?> getArticleById(Long id);
}