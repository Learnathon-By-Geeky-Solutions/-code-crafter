package com.xenon.core.domain.response.alert;

import com.xenon.data.entity.alert.AlertSeverity;
import com.xenon.data.entity.alert.AlertTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertResponse {
    private Long id;
    private String title;
    private String description;
    private String alertness;
    private double latitude;
    private double longitude;
    private double radius;
    private AlertSeverity severityLevel;
    private boolean isActive;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String healthAuthorizationName;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Double distanceFromUser; // Optional field for distance from user's location

    public static AlertResponse fromEntity(AlertTable alert) {
        return new AlertResponse(
                alert.getId(),
                alert.getTitle(),
                alert.getDescription(),
                alert.getAlertness(),
                alert.getLatitude(),
                alert.getLongitude(),
                alert.getRadius(),
                alert.getSeverityLevel(),
                alert.isActive(),
                alert.getStartDate(),
                alert.getEndDate(),
                alert.getHealthAuthorization().getUser().getFirstName() + " " +
                        alert.getHealthAuthorization().getUser().getLastName(),
                alert.getCreatedAt(),
                alert.getUpdatedAt(),
                null
        );
    }

    public static AlertResponse fromEntityWithDistance(AlertTable alert, Double distanceKm) {
        AlertResponse response = fromEntity(alert);
        response.setDistanceFromUser(distanceKm);
        return response;
    }
}