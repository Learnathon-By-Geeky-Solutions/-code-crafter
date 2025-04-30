package com.xenon.presenter.api.ambulance;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.ambulance.AmbulanceReviewRequest;
import com.xenon.core.domain.request.ambulance.CreateAmbulanceAccountRequest;
import com.xenon.core.service.ambulance.AmbulanceService;
import com.xenon.data.entity.ambulance.AmbulanceStatus;
import com.xenon.data.entity.ambulance.AmbulanceType;
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
    @PreAuthorize(authorities = {UserRole.AMBULANCE, UserRole.ADMIN, UserRole.USER}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createAmbulanceReviewRequest(@Nullable @RequestBody AmbulanceReviewRequest body) {
        return ambulanceService.createAmbulanceReviewRequest(body);
    }

    @GetMapping("list")
    @PreAuthorize(authorities = {UserRole.AMBULANCE, UserRole.ADMIN, UserRole.USER},shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAmbulanceList(
            @RequestParam AmbulanceType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return ambulanceService.getAmbulanceList(type, pageable);
    }

    @GetMapping("/{ambulanceId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAmbulanceById(@PathVariable Long ambulanceId) {
        return ambulanceService.getAmbulanceById(ambulanceId);
    }

    @GetMapping("/by-area")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAmbulancesByArea(
            @RequestParam AmbulanceType type,
            @RequestParam String area,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return ambulanceService.getAmbulancesByArea(type, area, pageable);
    }

    @GetMapping("reviews/{ambulanceId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAmbulanceReviews(
            @PathVariable Long ambulanceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return ambulanceService.getAmbulanceReviews(ambulanceId, pageable);
    }

    @PutMapping("/{ambulanceId}/status")
    @PreAuthorize(authorities = {UserRole.AMBULANCE, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateAmbulanceStatus(
            @PathVariable Long ambulanceId,
            @RequestParam AmbulanceStatus status) {
        return ambulanceService.updateAmbulanceStatus(ambulanceId, status);
    }

    @GetMapping("/can-review/{ambulanceId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> checkUserCanReview(@PathVariable Long ambulanceId) {
        return ambulanceService.checkUserCanReview(ambulanceId);
    }
}