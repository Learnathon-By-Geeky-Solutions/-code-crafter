package com.xenon.core.service.notification;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.service.common.BaseService;
import com.xenon.data.entity.notification.Notification;
import com.xenon.data.entity.user.User;
import com.xenon.data.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl extends BaseService implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public ResponseEntity<?> sendNotification(Long userId, String title, String message, String notificationType, Long relatedId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ClientException("User not found"));
            
            Notification notification = new Notification(user, title, message, notificationType, relatedId);
            notificationRepository.save(notification);
            
            // In a real-world scenario, you would also push this notification through WebSockets
            // or similar real-time communication channel
            
            return success("Notification sent successfully", null);
        } catch (Exception e) {
            log.error("Error sending notification: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getNotifications() {
        try {
            List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(getCurrentUser().getId());
            return success("Notifications retrieved successfully", notifications);
        } catch (Exception e) {
            log.error("Error retrieving notifications: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getUnreadNotifications() {
        try {
            List<Notification> notifications = notificationRepository.findByUserIdAndIsReadOrderByCreatedAtDesc(
                    getCurrentUser().getId(), false);
            return success("Unread notifications retrieved successfully", notifications);
        } catch (Exception e) {
            log.error("Error retrieving unread notifications: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> markAsRead(Long notificationId) {
        try {
            Notification notification = notificationRepository.findById(notificationId)
                    .orElseThrow(() -> new ClientException("Notification not found"));
            
            // Ensure the notification belongs to the current user
            if (!notification.getUser().getId().equals(getCurrentUser().getId())) {
                throw new ClientException("You do not have permission to access this notification");
            }
            
            notification.setIsRead(true);
            notificationRepository.save(notification);
            
            return success("Notification marked as read", null);
        } catch (Exception e) {
            log.error("Error marking notification as read: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> markAllAsRead() {
        try {
            List<Notification> unreadNotifications = notificationRepository.findByUserIdAndIsReadOrderByCreatedAtDesc(
                    getCurrentUser().getId(), false);
            
            for (Notification notification : unreadNotifications) {
                notification.setIsRead(true);
            }
            
            notificationRepository.saveAll(unreadNotifications);
            
            return success("All notifications marked as read", null);
        } catch (Exception e) {
            log.error("Error marking all notifications as read: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }
}