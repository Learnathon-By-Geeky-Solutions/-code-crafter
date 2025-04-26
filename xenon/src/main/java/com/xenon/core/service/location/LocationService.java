package com.xenon.core.service.location;

import com.xenon.core.domain.request.alert.UpdateUserLocationRequest;
import org.springframework.http.ResponseEntity;

public interface LocationService {
    /**
     * Updates the user's location information
     *
     * @param request The location update request containing latitude, longitude, and permission status
     * @return Response entity with result of the update operation
     */
    ResponseEntity<?> updateUserLocation(UpdateUserLocationRequest request);

    /**
     * Calculates the distance between two geographical points
     *
     * @param lat1 Latitude of first point
     * @param lon1 Longitude of first point
     * @param lat2 Latitude of second point
     * @param lon2 Longitude of second point
     * @return Distance in kilometers
     */
    double calculateDistance(double lat1, double lon1, double lat2, double lon2);

    /**
     * Checks if a point (user) is within the radius of another point (alert)
     *
     * @param userLat User's latitude
     * @param userLon User's longitude
     * @param alertLat Alert's latitude
     * @param alertLon Alert's longitude
     * @param radiusKm Alert's radius in kilometers
     * @return True if the user is within the alert radius, false otherwise
     */
    boolean isWithinRadius(double userLat, double userLon, double alertLat, double alertLon, double radiusKm);
}