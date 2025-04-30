package com.xenon.presenter.api.doctor;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.doctor.DoctorProfileRequest;
import com.xenon.core.service.doctor.DoctorService;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
@Tag(name = "Doctor Management", description = "APIs for managing doctor profiles")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/profile")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create doctor profile", description = "Create or update doctor profile")
    public ResponseEntity<?> createDoctorProfile(
            @Parameter(description = "Doctor profile details")
            @Nullable @RequestBody DoctorProfileRequest request) {
        return doctorService.createDoctorProfileRequest(request);
    }

    @GetMapping
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get doctor profile", description = "View a doctor's complete profile")
    public ResponseEntity<?> getDoctorProfile() {
        return doctorService.getDoctorProfile();
    }

    @GetMapping("/{doctorId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get doctor profile", description = "View a doctor's complete profile")
    public ResponseEntity<?> getDoctorProfile(
            @Parameter(description = "Doctor ID") @PathVariable Long doctorId) {
        return doctorService.getDoctorProfile(doctorId);
    }

    @PutMapping("/{doctorId}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update doctor profile", description = "Update an existing doctor profile")
    public ResponseEntity<?> updateDoctorProfile(
            @Parameter(description = "Doctor ID") @PathVariable Long doctorId,
            @Parameter(description = "Updated doctor profile details")
            @Nullable @RequestBody DoctorProfileRequest request) {
        return doctorService.updateDoctorProfile(doctorId, request);
    }
}