package com.xenon.presenter.api.pharmacy;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.pharmacy.CreatePharmacyAccountRequest;
import com.xenon.core.service.pharmacy.PharmacyService;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pharmacy")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
@Tag(name = "Pharmacy", description = "Pharmacy Management APIs")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @PostMapping
    @PreAuthorize(authorities = {UserRole.PHARMACY, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create new pharmacy", description = "Create a new pharmacy profile for an existing user")
    public ResponseEntity<?> createPharmacy(@Valid @RequestBody CreatePharmacyAccountRequest request) {
        return pharmacyService.createPharmacyRequest(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get pharmacy by ID", description = "Retrieve pharmacy details by ID")
    public ResponseEntity<?> getPharmacyById(@PathVariable Long id) {
        return pharmacyService.getPharmacyById(id);
    }

    @GetMapping
    @Operation(summary = "Get all pharmacies", description = "Retrieve all pharmacies with pagination")
    public ResponseEntity<?> getAllPharmacies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("ASC") ?
                Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return pharmacyService.getAllPharmacies(pageable);
    }

    @GetMapping("/search")
    @Operation(summary = "Search pharmacies by name", description = "Search pharmacies by owner name")
    public ResponseEntity<?> searchPharmacies(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("ASC") ?
                Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return pharmacyService.searchPharmacies(name, pageable);
    }

    @GetMapping("/by-upazila/{upazilaId}")
    @Operation(summary = "Get pharmacies by upazila", description = "Retrieve pharmacies filtered by upazila ID")
    public ResponseEntity<?> getPharmaciesByUpazilaId(
            @PathVariable Long upazilaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("ASC") ?
                Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return pharmacyService.getPharmaciesByUpazilaId(upazilaId, pageable);
    }
}