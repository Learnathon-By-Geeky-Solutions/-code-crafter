package com.xenon.presenter.api.notification;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.service.notification.NotificationService;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getNotifications() {
        return notificationService.getNotifications();
    }

    @GetMapping("/unread")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getUnreadNotifications() {
        return notificationService.getUnreadNotifications();
    }

    @PostMapping("/mark-read/{notificationId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) {
        return notificationService.markAsRead(notificationId);
    }

    @PostMapping("/mark-all-read")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> markAllAsRead() {
        return notificationService.markAllAsRead();
    }
}