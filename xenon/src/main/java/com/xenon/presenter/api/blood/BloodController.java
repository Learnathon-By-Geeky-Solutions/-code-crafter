package com.xenon.presenter.api.blood;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.blood.CreateBloodCommentRequest;
import com.xenon.core.domain.request.blood.CreateBloodRequestPost;
import com.xenon.core.service.blood.BloodCommentService;
import com.xenon.core.service.blood.BloodRequestPostService;
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
@RequestMapping("api/v1/blood")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class BloodController {

    private final BloodRequestPostService bloodRequestPostService;
    private final BloodCommentService bloodCommentService;

    @PostMapping("create-request")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.USER, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createBloodRequest(@Nullable @RequestBody CreateBloodRequestPost body) {
        return bloodRequestPostService.createBloodRequestPost(body);
    }

    @PostMapping("create-comment")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createBloodResponse(@Nullable @RequestBody CreateBloodCommentRequest body) {
        return bloodCommentService.createBloodCommentRequest(body);
    }

    @GetMapping("dashboard-blood")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getBloodDashboard() {
        return bloodRequestPostService.getBloodDashboard();
    }

    @GetMapping("blood-request-post-page")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getBloodRequestPostPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return bloodRequestPostService.getBloodRequestPostPage(pageable);
    }

    @GetMapping("blood-post-page")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getBloodPostPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return bloodRequestPostService.getBloodPostPage(pageable);
    }

    @GetMapping("requests/by-type")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getBloodRequestsByType(
            @RequestParam BloodType bloodType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return bloodRequestPostService.getBloodRequestsByType(bloodType, pageable);
    }

    @GetMapping("requests/by-location")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getBloodRequestsByLocation(
            @RequestParam Long upazilaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return bloodRequestPostService.getBloodRequestsByLocation(upazilaId, pageable);
    }

    @GetMapping("requests/filter")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getBloodRequestsByTypeAndLocation(
            @RequestParam BloodType bloodType,
            @RequestParam Long upazilaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return bloodRequestPostService.getBloodRequestsByTypeAndLocation(bloodType, upazilaId, pageable);
    }

    @GetMapping("requests/{requestId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getBloodRequestDetails(@PathVariable Long requestId) {
        return bloodRequestPostService.getBloodRequestDetails(requestId);
    }
}