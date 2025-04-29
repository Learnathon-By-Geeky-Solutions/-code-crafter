package com.xenon.core.service.alert;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.response.alert.AlertResponse;
import com.xenon.core.domain.response.alert.UserAlertNotificationResponse;
import com.xenon.core.service.common.BaseService;
import com.xenon.core.service.location.LocationService;
import com.xenon.core.service.location.UserLocationChangedEvent;
import com.xenon.data.entity.alert.AlertTable;
import com.xenon.data.entity.alert.UserAlertNotification;
import com.xenon.data.entity.alert.UserLocation;
import com.xenon.data.entity.user.User;
import com.xenon.data.repository.AlertTableRepository;
import com.xenon.data.repository.UserAlertNotificationRepository;
import com.xenon.data.repository.UserLocationRepository;
import com.xenon.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertNotificationServiceUserImpl extends BaseService implements AlertNotificationServiceUser {

    private final AlertTableRepository alertTableRepository;
    private final UserAlertNotificationRepository notificationRepository;
    private final UserLocationRepository userLocationRepository;
    private final UserRepository userRepository;
    private final LocationService locationService;

    /**
     * Listen for user location changed events and process them
     */
    @EventListener
    @Async
    @Transactional
    public void handleUserLocationChangedEvent(UserLocationChangedEvent event) {
        try {
            processNewLocationForAlerts(event.getUserId(), event.getLatitude(), event.getLongitude());
        } catch (Exception e) {
            log.error("Error handling user location changed event: {}", e.getMessage(), e);
        }
    }

    @Override
    @Async
    @Transactional
    public void processNewLocationForAlerts(Long userId, double latitude, double longitude) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ClientException("User not found"));

            // Find all active alerts in the user's area
            ZonedDateTime now = ZonedDateTime.now();
            List<AlertTable> nearbyAlerts = alertTableRepository.findActiveAlertsInRadius(latitude, longitude, now);

            List<UserAlertNotificationResponse> newNotifications = new ArrayList<>();

            for (AlertTable alert : nearbyAlerts) {
                // Check if the user already has a notification for this alert
                boolean notificationExists = notificationRepository.existsByUser_IdAndAlert_Id(userId, alert.getId());

                if (!notificationExists) {
                    // Create a new notification
                    UserAlertNotification notification = new UserAlertNotification(user, alert);
                    notification = notificationRepository.save(notification);

                    // Calculate distance
                    double distance = locationService.calculateDistance(
                            latitude, longitude,
                            alert.getLatitude(), alert.getLongitude()
                    );

                    newNotifications.add(
                            UserAlertNotificationResponse.fromEntityWithDistance(notification, distance));
                }
            }

        } catch (Exception e) {
            log.error("Error processing user location for alerts: {}", e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<?> getCurrentUserAlertNotifications(Pageable pageable) {
        try {
            User currentUser = getCurrentUser();

            Page<UserAlertNotification> notifications =
                    notificationRepository.findByUser_IdOrderByCreatedAtDesc(currentUser.getId(), pageable);

            // Get user's current location for distance calculation
            Optional<UserLocation> userLocationOpt = userLocationRepository.findByUser_Id(currentUser.getId());

            Page<UserAlertNotificationResponse> responsePage = notifications.map(notification -> {
                if (userLocationOpt.isPresent() && userLocationOpt.get().isLocationAllowed()) {
                    UserLocation userLocation = userLocationOpt.get();
                    double distance = locationService.calculateDistance(
                            userLocation.getLatitude(), userLocation.getLongitude(),
                            notification.getAlert().getLatitude(), notification.getAlert().getLongitude()
                    );
                    return UserAlertNotificationResponse.fromEntityWithDistance(notification, distance);
                } else {
                    return UserAlertNotificationResponse.fromEntity(notification);
                }
            });

            return success("Alert notifications retrieved successfully", responsePage);
        } catch (Exception e) {
            log.error("Error retrieving alert notifications: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getUnreadAlertNotifications() {
        try {
            User currentUser = getCurrentUser();

            List<UserAlertNotification> unreadNotifications =
                    notificationRepository.findByUser_IdAndIsReadFalseOrderByCreatedAtDesc(currentUser.getId());

            // Get user's current location for distance calculation
            Optional<UserLocation> userLocationOpt = userLocationRepository.findByUser_Id(currentUser.getId());

            List<UserAlertNotificationResponse> responseList = unreadNotifications.stream()
                    .map(notification -> {
                        if (userLocationOpt.isPresent() && userLocationOpt.get().isLocationAllowed()) {
                            UserLocation userLocation = userLocationOpt.get();
                            double distance = locationService.calculateDistance(
                                    userLocation.getLatitude(), userLocation.getLongitude(),
                                    notification.getAlert().getLatitude(), notification.getAlert().getLongitude()
                            );
                            return UserAlertNotificationResponse.fromEntityWithDistance(notification, distance);
                        } else {
                            return UserAlertNotificationResponse.fromEntity(notification);
                        }
                    })
                    .collect(Collectors.toList());

            return success("Unread alert notifications retrieved successfully", responseList);
        } catch (Exception e) {
            log.error("Error retrieving unread alert notifications: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> markNotificationAsRead(Long notificationId) {
        try {
            User currentUser = getCurrentUser();

            UserAlertNotification notification = notificationRepository.findById(notificationId)
                    .orElseThrow(() -> new ClientException("Notification not found"));

            // Verify the notification belongs to the current user
            if (!notification.getUser().getId().equals(currentUser.getId())) {
                throw new ClientException("Notification does not belong to the current user");
            }

            notification.markAsRead();
            notificationRepository.save(notification);

            return success("Notification marked as read", null);
        } catch (ClientException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error marking notification as read: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> markAllNotificationsAsRead() {
        try {
            User currentUser = getCurrentUser();

            List<UserAlertNotification> unreadNotifications =
                    notificationRepository.findByUser_IdAndIsReadFalseOrderByCreatedAtDesc(currentUser.getId());

            ZonedDateTime now = ZonedDateTime.now();
            for (UserAlertNotification notification : unreadNotifications) {
                notification.markAsRead();
            }

            if (!unreadNotifications.isEmpty()) {
                notificationRepository.saveAll(unreadNotifications);
            }

            return success("All notifications marked as read", null);
        } catch (Exception e) {
            log.error("Error marking all notifications as read: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getNearbyAlerts() {
        try {
            User currentUser = getCurrentUser();

            // Get user's current location
            UserLocation userLocation = userLocationRepository.findByUser_Id(currentUser.getId())
                    .orElseThrow(() -> new ClientException("User location not found. Please enable location sharing."));

            if (!userLocation.isLocationAllowed()) {
                throw new ClientException("Location sharing is disabled. Please enable it to see nearby alerts.");
            }

            // Find active alerts near the user's location
            ZonedDateTime now = ZonedDateTime.now();
            List<AlertTable> nearbyAlerts = alertTableRepository.findActiveAlertsInRadius(
                    userLocation.getLatitude(), userLocation.getLongitude(), now);

            List<AlertResponse> responseList = nearbyAlerts.stream()
                    .map(alert -> {
                        double distance = locationService.calculateDistance(
                                userLocation.getLatitude(), userLocation.getLongitude(),
                                alert.getLatitude(), alert.getLongitude()
                        );
                        return AlertResponse.fromEntityWithDistance(alert, distance);
                    })
                    .collect(Collectors.toList());

            return success("Nearby alerts retrieved successfully", responseList);
        } catch (ClientException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving nearby alerts: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }
}