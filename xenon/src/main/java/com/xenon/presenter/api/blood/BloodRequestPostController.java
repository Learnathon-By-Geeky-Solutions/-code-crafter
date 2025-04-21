package com.xenon.presenter.api.blood;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.blood.CreateBloodRequestPost;
import com.xenon.core.service.blood.BloodRequestPostService;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/blood-request")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class BloodRequestPostController {

    private final BloodRequestPostService bloodRequestPostService;

    @PostMapping("create")
    @PreAuthorize(authorities = {UserRole.DOCTOR,UserRole.USER, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createBloodRequest(@Nullable @RequestBody CreateBloodRequestPost body) {
        return bloodRequestPostService.createBloodRequestPost(body);
    }
}
