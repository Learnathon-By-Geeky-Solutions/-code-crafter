package com.xenon.presenter.api.pharmacy;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.pharmacy.CreatePharmacyAccountRequest;
import com.xenon.core.service.pharmacy.PharmacyService;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pharmacy")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @PostMapping("create")
    @PreAuthorize(authorities = {UserRole.PHARMACY, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> create(@Nullable @RequestBody CreatePharmacyAccountRequest body) {
        return pharmacyService.createPharmacyRequest(body);
    }
}
