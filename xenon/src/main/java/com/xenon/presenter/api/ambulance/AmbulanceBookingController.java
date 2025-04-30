package com.xenon.presenter.api.ambulance;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.ambulance.AmbulanceBookingRequest;
import com.xenon.core.domain.request.ambulance.UpdateBookingStatusRequest;
import com.xenon.core.service.ambulance.AmbulanceBookingService;
import com.xenon.data.entity.ambulance.AmbulanceBookingStatus;
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
@RequestMapping("api/v1/ambulance/booking")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class AmbulanceBookingController {

    private final AmbulanceBookingService ambulanceBookingService;

    @PostMapping("create")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createBooking(@Nullable @RequestBody AmbulanceBookingRequest body) {
        return ambulanceBookingService.createBooking(body);
    }

    @PutMapping("update-status")
    @PreAuthorize(authorities = {UserRole.AMBULANCE, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateBookingStatus(@Nullable
                                                 @RequestParam AmbulanceBookingStatus status,
                                                 @RequestBody UpdateBookingStatusRequest body) {

        if (status == null) throw new ClientException("Status cannot be null");
        return ambulanceBookingService.updateBookingStatus(body, status);
    }

    @GetMapping("user-bookings")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getUserBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "bookingTime") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return ambulanceBookingService.getUserBookings(pageable);
    }

    @GetMapping("ambulance-bookings/{ambulanceId}")
    @PreAuthorize(authorities = {UserRole.AMBULANCE, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAmbulanceBookings(
            @PathVariable Long ambulanceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "bookingTime") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return ambulanceBookingService.getAmbulanceBookings(ambulanceId, pageable);
    }

    @GetMapping("{bookingId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getBookingDetails(@PathVariable Long bookingId) {
        return ambulanceBookingService.getBookingDetails(bookingId);
    }

    @PutMapping("cancel/{bookingId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        return ambulanceBookingService.cancelBooking(bookingId);
    }
}