package com.xenon.presenter.api.blog;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.blog.CreateBlogPostRequest;
import com.xenon.core.service.blog.BlogService;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class BlogController {

    private final BlogService blogService;

    @PostMapping("create")
    @PreAuthorize(authorities = {UserRole.ADMIN,UserRole.USER}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createBlogPostRequest(@Nullable @RequestBody CreateBlogPostRequest body) {
        return blogService.createBlogPostRequest(body);
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize
    public ResponseEntity<?> getBlogPosts() {return blogService.getBlogPosts();}
}
