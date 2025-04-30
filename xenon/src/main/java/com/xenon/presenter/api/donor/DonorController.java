package com.xenon.presenter.api.donor;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.donor.BloodDonationInfoRequest;
import com.xenon.core.domain.request.donor.CreateDonorAccountRequest;
import com.xenon.core.domain.request.donor.UpdateDonorInterestRequest;
import com.xenon.core.service.donor.DonorService;
import com.xenon.data.entity.donor.BloodType;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @PreAuthorize(authorities = {UserRole.USER, UserRole.ADMIN}, shouldCheckAccountStatus = true)
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

    @GetMapping("profile")
    @PreAuthorize(authorities = {UserRole.USER, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getDonorProfile() {
        return donorService.getDonorProfile();
    }

    @PutMapping("update-interest")
    @PreAuthorize(authorities = {UserRole.USER, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateDonorInterest(@Nullable @RequestBody UpdateDonorInterestRequest body) {
        return donorService.updateDonorInterest(body);
    }

    @GetMapping("available")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAvailableDonors(
            @RequestParam(required = false) BloodType bloodType,
            @RequestParam(required = false) Long upazilaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return donorService.getAvailableDonors(bloodType, upazilaId, pageable);
    }
}