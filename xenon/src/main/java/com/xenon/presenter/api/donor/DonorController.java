package com.xenon.presenter.api.donor;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.donor.BloodDonationInfoRequest;
import com.xenon.core.domain.request.donor.CreateDonorAccountRequest;
import com.xenon.core.service.donor.DonorService;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/donor")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class DonorController {

    private final DonorService donorService;

    @PostMapping("create")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN,UserRole.USER}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createDonorAccountRequest(@Nullable @RequestBody CreateDonorAccountRequest body) {
        return donorService.createDonorAccountRequest(body);
    }

    @PostMapping("blood-given")
    @PreAuthorize(authorities = {UserRole.USER, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> bloodGiven(@Nullable @RequestBody BloodDonationInfoRequest body) {
        return donorService.bloodGivenInfoRequest(body);
    }

    @GetMapping("donation-history")
    @PreAuthorize(authorities = {UserRole.USER, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getDonationHistory() {
        return donorService.getDonationHistory();
    }
}
