package com.xenon.core.service.alert;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AlertNotificationServiceUser {
    /**
     * Process a user's new location to check for nearby alerts
     *
     * @param userId    The ID of the user
     * @param latitude  The user's latitude
     * @param longitude The user's longitude
     */
    void processNewLocationForAlerts(Long userId, double latitude, double longitude);

    /**
     * Get the current user's alert notifications with pagination
     *
     * @param pageable Pagination information
     * @return Response with paginated alert notifications
     */
    ResponseEntity<?> getCurrentUserAlertNotifications(Pageable pageable);

    /**
     * Get the current user's unread alert notifications
     *
     * @return Response with unread alert notifications
     */
    ResponseEntity<?> getUnreadAlertNotifications();

    /**
     * Mark a specific notification as read
     *
     * @param notificationId ID of the notification to mark as read
     * @return Response indicating success or failure
     */
    ResponseEntity<?> markNotificationAsRead(Long notificationId);

    /**
     * Mark all the current user's notifications as read
     *
     * @return Response indicating success or failure
     */
    ResponseEntity<?> markAllNotificationsAsRead();

    /**
     * Get alerts near the current user's location
     *
     * @return Response with nearby alerts
     */
    ResponseEntity<?> getNearbyAlerts();
}