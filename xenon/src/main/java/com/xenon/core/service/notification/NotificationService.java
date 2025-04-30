package com.xenon.core.service.notification;

import org.springframework.http.ResponseEntity;

public interface NotificationService {
    
    ResponseEntity<?> sendNotification(Long userId, String title, String message, String notificationType, Long relatedId);
    
    ResponseEntity<?> getNotifications();
    
    ResponseEntity<?> getUnreadNotifications();
    
    ResponseEntity<?> markAsRead(Long notificationId);
    
    ResponseEntity<?> markAllAsRead();
}