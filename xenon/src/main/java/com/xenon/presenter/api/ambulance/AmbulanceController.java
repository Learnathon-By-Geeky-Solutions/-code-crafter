package com.xenon.presenter.api.ambulance;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.ambulance.AmbulanceReviewRequest;
import com.xenon.core.domain.request.ambulance.CreateAmbulanceAccountRequest;
import com.xenon.core.service.ambulance.AmbulanceService;
import com.xenon.data.entity.ambulance.AmbulanceType;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ambulance")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class AmbulanceController {

    private final AmbulanceService ambulanceService;

    @PostMapping("create")
    @PreAuthorize(authorities = {UserRole.AMBULANCE, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createAmbulanceRequest(@Nullable @RequestBody CreateAmbulanceAccountRequest body) {
        return ambulanceService.createAmbulanceRequest(body);
    }

    @PostMapping("create-ambulance-review")
    @PreAuthorize(authorities = {UserRole.USER, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createAmbulanceReviewRequest(@Nullable @RequestBody AmbulanceReviewRequest body) {
        return ambulanceService.createAmbulanceReviewRequest(body);
    }
    
    @GetMapping
    public ResponseEntity<?> getAmbulanceList(@Param("type") AmbulanceType type) {
        return null;
    }


}
