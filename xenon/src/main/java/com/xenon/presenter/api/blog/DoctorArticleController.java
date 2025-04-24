package com.xenon.presenter.api.blog;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.blog.BlogPostRequest;
import com.xenon.core.service.blog.doctorArticle.DoctorArticleService;
import com.xenon.data.entity.blood.BloodRequestPost;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctor/articles")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class DoctorArticleController {

    private final DoctorArticleService doctorArticleService;

    @PostMapping("/create")
    @PreAuthorize(authorities = {UserRole.DOCTOR}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createArticle(@RequestBody BlogPostRequest body) {
        return doctorArticleService.createArticle(body);
    }

    @GetMapping
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

    @PutMapping("/{id}")
    @PreAuthorize(authorities = {UserRole.DOCTOR}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @RequestBody BlogPostRequest body) {
        return doctorArticleService.updateArticle(id, body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        return doctorArticleService.deleteArticle(id);
    }

}
