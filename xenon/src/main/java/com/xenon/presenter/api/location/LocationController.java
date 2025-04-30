package com.xenon.presenter.api.location;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.alert.UpdateUserLocationRequest;
import com.xenon.core.service.location.LocationService;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/location")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
@Tag(name = "Location API", description = "Endpoints for managing user locations")
public class LocationController {

    private final LocationService locationService;

    @PutMapping("update")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update user location", 
               description = "Updates the current user's location and permission status")
    public ResponseEntity<?> updateLocation(@Nullable @RequestBody UpdateUserLocationRequest body) {
        return locationService.updateUserLocation(body);
    }
}