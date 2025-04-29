package com.xenon.presenter.api.blog;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.blog.BlogPostRequest;
import com.xenon.core.service.blog.doctorArticle.DoctorArticleService;
import com.xenon.data.entity.blog.doctorArticle.DoctorArticleCategory;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/doctor/articles")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class DoctorArticleController {

    private final DoctorArticleService doctorArticleService;

    @PostMapping("/create")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createArticle(
            @RequestBody BlogPostRequest body,
            @RequestParam String category) {
        try {
            // Convert string category to enum
            DoctorArticleCategory doctorCategory = DoctorArticleCategory.valueOf(category);
            return doctorArticleService.createArticle(body, doctorCategory);
        } catch (IllegalArgumentException e) {
            throw new com.xenon.core.domain.exception.ClientException("Invalid doctor category: " + category);
        }
    }

    /*@PostMapping("/create/{category}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createArticleWithCategory(
            @RequestBody BlogPostRequest body,
            @PathVariable DoctorArticleCategory category
    ) {
        return doctorArticleService.createArticleWithCategory(body, category);
    }*/

    @GetMapping
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAllArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return doctorArticleService.getAllArticles(pageable);
    }

    @GetMapping("/categories/{category}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getArticlesByDoctorCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return doctorArticleService.getArticlesByDoctorCategory(category, pageable);
    }


    /*@GetMapping("/trending/{trendingBy}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getTrendingArticles(
            @PathVariable String trendingBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorArticleService.getTrendingArticles(trendingBy, pageable);
    }*/

    @GetMapping("/search")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> searchArticles(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return doctorArticleService.searchArticles(query, pageable);
    }


    @GetMapping("/{id}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getArticleById(@PathVariable Long id) {
        return doctorArticleService.getArticleById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @RequestBody BlogPostRequest body) {
        return doctorArticleService.updateArticle(id, body);
    }

    @PutMapping("/{id}/categories/{category}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateArticleWithCategory(
            @PathVariable Long id,
            @RequestBody BlogPostRequest body,
            @RequestParam DoctorArticleCategory category
    ) {
        return doctorArticleService.updateArticleWithCategory(id, body, category);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        return doctorArticleService.deleteArticle(id);
    }
}