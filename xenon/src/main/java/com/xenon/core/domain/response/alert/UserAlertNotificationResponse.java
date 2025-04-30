package com.xenon.core.domain.response.alert;

import com.xenon.data.entity.alert.AlertSeverity;
import com.xenon.data.entity.alert.UserAlertNotification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAlertNotificationResponse {
    private Long id;
    private Long alertId;
    private String title;
    private String description;
    private String alertness;
    private AlertSeverity severityLevel;
    private boolean isRead;
    private ZonedDateTime createdAt;
    private ZonedDateTime readAt;
    private Double distance; // Distance in kilometers
    
    public static UserAlertNotificationResponse fromEntity(UserAlertNotification notification) {
        return new UserAlertNotificationResponse(
            notification.getId(),
            notification.getAlert().getId(),
            notification.getAlert().getTitle(),
            notification.getAlert().getDescription(),
            notification.getAlert().getAlertness(),
            notification.getAlert().getSeverityLevel(),
            notification.isRead(),
            notification.getCreatedAt(),
            notification.getReadAt(),
            null
        );
    }
    
    public static UserAlertNotificationResponse fromEntityWithDistance(
            UserAlertNotification notification, Double distanceKm) {
        UserAlertNotificationResponse response = fromEntity(notification);
        response.setDistance(distanceKm);
        return response;
    }
}