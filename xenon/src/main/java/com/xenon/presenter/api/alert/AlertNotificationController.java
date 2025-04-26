package com.xenon.presenter.api.alert;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.service.alert.AlertNotificationServiceUser;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/alert")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
@Tag(name = "Alert Notification API", description = "Endpoints for managing user alert notifications")
public class AlertNotificationController {

    private final AlertNotificationServiceUser alertNotificationService;
    
    @GetMapping("notifications")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get User Notifications", 
               description = "Gets all alert notifications for the current user")
    public ResponseEntity<?> getUserNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? 
                Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        return alertNotificationService.getCurrentUserAlertNotifications(pageable);
    }
    
    @GetMapping("notifications/unread")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get Unread Notifications", 
               description = "Gets unread alert notifications for the current user")
    public ResponseEntity<?> getUnreadNotifications() {
        return alertNotificationService.getUnreadAlertNotifications();
    }
    
    @PutMapping("notifications/{notificationId}/read")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Mark Notification as Read", 
               description = "Marks a specific notification as read")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long notificationId) {
        return alertNotificationService.markNotificationAsRead(notificationId);
    }
    
    @PutMapping("notifications/read-all")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Mark All Notifications as Read", 
               description = "Marks all notifications as read for the current user")
    public ResponseEntity<?> markAllNotificationsAsRead() {
        return alertNotificationService.markAllNotificationsAsRead();
    }
    
    @GetMapping("nearby")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get Nearby Alerts", 
               description = "Gets all active alerts near the user's current location")
    public ResponseEntity<?> getNearbyAlerts() {
        return alertNotificationService.getNearbyAlerts();
    }
}