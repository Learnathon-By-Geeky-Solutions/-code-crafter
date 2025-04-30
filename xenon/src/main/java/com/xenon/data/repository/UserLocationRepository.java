package com.xenon.data.repository;

import com.xenon.data.entity.alert.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    
    Optional<UserLocation> findByUser_Id(Long userId);
    
    // Find users whose location was updated in the last X minutes
    @Query("SELECT ul FROM UserLocation ul WHERE ul.locationAllowed = true AND ul.lastUpdated >= :timeThreshold")
    List<UserLocation> findActiveUserLocations(@Param("timeThreshold") ZonedDateTime timeThreshold);
    
    // Find users within a specific radius of a point
    @Query(value = """
            SELECT ul FROM UserLocation ul 
            WHERE ul.locationAllowed = true 
            AND (6371 * acos(cos(radians(:latitude)) * cos(radians(ul.latitude)) 
                 * cos(radians(ul.longitude) - radians(:longitude)) 
                 + sin(radians(:latitude)) * sin(radians(ul.latitude)))) <= :radius
            """)
    List<UserLocation> findUsersWithinRadius(
            @Param("latitude") double latitude, 
            @Param("longitude") double longitude, 
            @Param("radius") double radius);
}