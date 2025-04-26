package com.xenon.core;

import com.xenon.core.service.alert.AlertNotificationServiceUser;
import com.xenon.data.entity.alert.UserLocation;
import com.xenon.data.repository.UserLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Scheduled task to periodically check for new alerts for users who have
 * location sharing enabled.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AlertTaskScheduler {

    private final UserLocationRepository userLocationRepository;
    private final AlertNotificationServiceUser alertNotificationService;
    
    /**
     * Every 15 minutes, checks for active users and processes new alerts
     * for them based on their current locations.
     */
    @Scheduled(fixedRate = 900000) // 15 minutes in milliseconds
    public void checkActiveUsersForAlerts() {
        log.info("Starting scheduled alert check for active users");
        
        try {
            // Find users whose location was updated in the last hour
            ZonedDateTime oneHourAgo = ZonedDateTime.now().minusHours(1);
            List<UserLocation> activeUsers = userLocationRepository.findActiveUserLocations(oneHourAgo);
            
            log.info("Found {} active users to check for alerts", activeUsers.size());
            
            for (UserLocation userLocation : activeUsers) {
                try {
                    alertNotificationService.processNewLocationForAlerts(
                            userLocation.getUser().getId(),
                            userLocation.getLatitude(),
                            userLocation.getLongitude()
                    );
                } catch (Exception e) {
                    log.error("Error processing alerts for user {}: {}", 
                            userLocation.getUser().getId(), e.getMessage(), e);
                }
            }
            
            log.info("Completed scheduled alert check for active users");
        } catch (Exception e) {
            log.error("Error during scheduled alert check: {}", e.getMessage(), e);
        }
    }
}