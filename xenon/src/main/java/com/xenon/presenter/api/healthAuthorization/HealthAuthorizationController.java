package com.xenon.presenter.api.healthAuthorization;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.healthAuthorization.CreateAlertRequest;
import com.xenon.core.domain.request.healthAuthorization.CreateHealthAuthorizationAccountRequest;
import com.xenon.core.service.healthAuthorization.HealthAuthorizationService;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/health-authorization")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class HealthAuthorizationController {

    private final HealthAuthorizationService healthAuthorizationService;

    @PostMapping("create")
    @PreAuthorize(authorities = {UserRole.HEALTH_AUTHORIZATION, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> create(@Nullable @RequestBody CreateHealthAuthorizationAccountRequest body) {
        return healthAuthorizationService.createHealthAuthorizationRequest(body);
    }

    @PutMapping("post-alert")
    @PreAuthorize(authorities = {UserRole.HEALTH_AUTHORIZATION, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> postAlert(@Nullable @RequestBody CreateAlertRequest body) {
        return healthAuthorizationService.createAlertRequest(body);
    }
}
