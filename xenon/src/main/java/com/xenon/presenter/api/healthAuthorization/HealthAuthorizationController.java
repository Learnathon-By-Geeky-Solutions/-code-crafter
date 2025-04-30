package com.xenon.presenter.api.healthAuthorization;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.healthAuthorization.CreateAlertRequest;
import com.xenon.core.domain.request.healthAuthorization.CreateHealthAuthorizationAccountRequest;
import com.xenon.core.domain.request.healthAuthorization.UpdateAlertRequest;
import com.xenon.core.service.healthAuthorization.HealthAuthorizationService;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/health-authorization")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
@Tag(name = "Health Authorization API", description = "Endpoints for health authorization management and alerts")
public class HealthAuthorizationController {

    private final HealthAuthorizationService healthAuthorizationService;

    @PostMapping("create")
    @PreAuthorize(authorities = {UserRole.HEALTH_AUTHORIZATION, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create Health Authorization Account",
            description = "Creates a new health authorization account for the current user")
    public ResponseEntity<?> create(@Nullable @RequestBody CreateHealthAuthorizationAccountRequest body) {
        return healthAuthorizationService.createHealthAuthorizationRequest(body);
    }

    @PostMapping("alert")
    @PreAuthorize(authorities = {UserRole.HEALTH_AUTHORIZATION}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create Alert",
            description = "Creates a new health alert with geolocation information")
    public ResponseEntity<?> createAlert(@Nullable @RequestBody CreateAlertRequest body) {
        return healthAuthorizationService.createAlertRequest(body);
    }

    @PutMapping("alert/{alertId}")
    @PreAuthorize(authorities = {UserRole.HEALTH_AUTHORIZATION, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update Alert",
            description = "Updates an existing health alert")
    public ResponseEntity<?> updateAlert(
            @PathVariable Long alertId,
            @Nullable @RequestBody UpdateAlertRequest body) {
        return healthAuthorizationService.updateAlertRequest(alertId, body);
    }

    @PutMapping("alert/{alertId}/deactivate")
    @PreAuthorize(authorities = {UserRole.HEALTH_AUTHORIZATION, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Deactivate Alert",
            description = "Deactivates an existing health alert (soft delete)")
    public ResponseEntity<?> deactivateAlert(@PathVariable Long alertId) {
        return healthAuthorizationService.deactivateAlert(alertId);
    }

    @DeleteMapping("alert/{alertId}")
    @PreAuthorize(authorities = {UserRole.HEALTH_AUTHORIZATION, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete Alert",
            description = "Permanently deletes an existing health alert")
    public ResponseEntity<?> deleteAlert(@PathVariable Long alertId) {
        return healthAuthorizationService.deleteAlert(alertId);
    }

    @GetMapping("alerts")
    @PreAuthorize(authorities = {UserRole.HEALTH_AUTHORIZATION, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get All Alerts",
            description = "Gets all alerts created by the current health authorization")
    public ResponseEntity<?> getAllAlerts() {
        return healthAuthorizationService.getAllAlerts();
    }

    @GetMapping("alert/{alertId}")
    @PreAuthorize(authorities = {UserRole.HEALTH_AUTHORIZATION, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get Alert by ID",
            description = "Gets a specific alert by ID")
    public ResponseEntity<?> getAlertById(@PathVariable Long alertId) {
        return healthAuthorizationService.getAlertById(alertId);
    }
}