package com.xenon.presenter.api.blog;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.blog.CreateBlogPostRequest;
import com.xenon.core.service.blog.BlogService;
import com.xenon.data.entity.user.UserRole;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
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
