package com.xenon.presenter.api.blog;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.blog.CreateLikeRequest;
import com.xenon.core.service.blog.LikeService;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class LikeController {

    private final LikeService likeService;

    @PostMapping("create")
    @PreAuthorize(authorities = {UserRole.USER, UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> create(@Nullable @RequestBody CreateLikeRequest body) {
        return likeService.createLikeRequest(body);
    }
}
