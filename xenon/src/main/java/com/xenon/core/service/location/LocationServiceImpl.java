package com.xenon.core.service.location;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.request.alert.UpdateUserLocationRequest;
import com.xenon.core.service.common.BaseService;
import com.xenon.data.entity.alert.UserLocation;
import com.xenon.data.entity.user.User;
import com.xenon.data.repository.UserLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl extends BaseService implements LocationService {

    private final UserLocationRepository userLocationRepository;
    private final ApplicationEventPublisher eventPublisher;

    private static final double EARTH_RADIUS_KM = 6371.0; // Earth's radius in kilometers

    @Override
    @Transactional
    public ResponseEntity<?> updateUserLocation(UpdateUserLocationRequest request) {
        User currentUser = getCurrentUser();

        try {
            // Validate request
            if (request.getLatitude() == null || request.getLongitude() == null) {
                throw clientException("Latitude and longitude are required");
            }

            // Validate coordinates
            if (request.getLatitude() < -90 || request.getLatitude() > 90) {
                throw clientException("Latitude must be between -90 and 90 degrees");
            }

            if (request.getLongitude() < -180 || request.getLongitude() > 180) {
                throw clientException("Longitude must be between -180 and 180 degrees");
            }

            // Find existing user location or create new one
            Optional<UserLocation> existingLocation = userLocationRepository.findByUser_Id(currentUser.getId());
            UserLocation userLocation;

            boolean locationChanged = false;

            if (existingLocation.isPresent()) {
                userLocation = existingLocation.get();

                // Check if location has significantly changed (more than 0.1 km or ~100 meters)
                if (userLocation.getLatitude() != null && userLocation.getLongitude() != null) {
                    double distance = calculateDistance(
                            userLocation.getLatitude(), userLocation.getLongitude(),
                            request.getLatitude(), request.getLongitude()
                    );
                    locationChanged = distance > 0.1;
                }

                // Update existing location
                userLocation.setLatitude(request.getLatitude());
                userLocation.setLongitude(request.getLongitude());

                // Update location permission if provided
                if (request.getLocationAllowed() != null) {
                    userLocation.setLocationAllowed(request.getLocationAllowed());
                }
            } else {
                // Create new location entry
                boolean locationAllowed = request.getLocationAllowed() != null ? request.getLocationAllowed() : true;
                userLocation = new UserLocation(currentUser, request.getLatitude(), request.getLongitude(), locationAllowed);
                locationChanged = true;
            }

            // Save the location
            userLocation.setLastUpdated(ZonedDateTime.now());
            userLocation = userLocationRepository.save(userLocation);

            // If location has changed significantly, publish an event instead of directly calling the service
            if (locationChanged && userLocation.isLocationAllowed()) {
                // Publish the event instead of calling the service directly
                UserLocationChangedEvent event = new UserLocationChangedEvent(
                        currentUser.getId(),
                        request.getLatitude(),
                        request.getLongitude()
                );
                eventPublisher.publishEvent(event);
            }

            return success("Location updated successfully", null);
        } catch (Exception e) {
            log.error("Error updating user location: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Convert degrees to radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Haversine formula
        double dlon = lon2Rad - lon1Rad;
        double dlat = lat2Rad - lat1Rad;
        double a = Math.pow(Math.sin(dlat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance in kilometers
        return EARTH_RADIUS_KM * c;
    }

    @Override
    public boolean isWithinRadius(double userLat, double userLon, double alertLat, double alertLon, double radiusKm) {
        double distance = calculateDistance(userLat, userLon, alertLat, alertLon);
        return distance <= radiusKm;
    }
}