package com.xenon.presenter.api.doctor;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.doctor.CreateDoctorProfileRequest;
import com.xenon.core.service.doctor.DoctorService;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/doctor")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("create")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> create(@Nullable @RequestBody CreateDoctorProfileRequest body) {
        return doctorService.createDoctorProfileRequest(body);
    }
}
