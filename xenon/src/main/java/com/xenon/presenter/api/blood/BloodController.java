package com.xenon.presenter.api.blood;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.blood.CreateBloodCommentRequest;
import com.xenon.core.domain.request.blood.CreateBloodRequestPost;
import com.xenon.core.service.blood.BloodCommentService;
import com.xenon.core.service.blood.BloodRequestPostService;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/blood")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class BloodController {

    private final BloodRequestPostService bloodRequestPostService;
    private final BloodCommentService bloodCommentService;

    @PostMapping("create-request")
    @PreAuthorize(authorities = {UserRole.DOCTOR,UserRole.USER, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createBloodRequest(@Nullable @RequestBody CreateBloodRequestPost body) {
        return bloodRequestPostService.createBloodRequestPost(body);
    }

    @PostMapping("create-comment")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createBloodResponse(@io.micrometer.common.lang.Nullable @RequestBody CreateBloodCommentRequest body) {
        return bloodCommentService.createBloodCommentRequest(body);
    }

    @GetMapping("dashboard-blood")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getBloodDashboard() {
        return bloodRequestPostService.getBloodDashboard();
    }

    @GetMapping("blood-request-post-page")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getBloodRequestPostPage() {
        return bloodRequestPostService.getBloodRequestPostPage();
    }

    @GetMapping ("blood-post-page")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getBloodPostPage() {
        return bloodRequestPostService.getBloodPostPage();
    }


}
