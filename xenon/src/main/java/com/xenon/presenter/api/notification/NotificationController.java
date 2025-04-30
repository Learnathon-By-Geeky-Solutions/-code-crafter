package com.xenon.presenter.api.notification;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.service.notification.NotificationService;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Controller", description = "Endpoints for managing user notifications")
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    @Operation(summary = "Send a notification to a user")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> sendNotification(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam String notificationType,
            @RequestParam(required = false) Long relatedId) {
        return notificationService.sendNotification(userId, title, message, notificationType, relatedId);
    }

    @GetMapping
    @Operation(summary = "Get all notifications for the current user")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getNotifications() {
        return notificationService.getNotifications();
    }

    @GetMapping("/unread")
    @Operation(summary = "Get all unread notifications for the current user")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getUnreadNotifications() {
        return notificationService.getUnreadNotifications();
    }

    @PutMapping("/{notificationId}/read")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Mark a notification as read")
    public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) {
        return notificationService.markAsRead(notificationId);
    }

    @PutMapping("/read-all")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Mark all notifications as read")
    public ResponseEntity<?> markAllAsRead() {
        return notificationService.markAllAsRead();
    }
}