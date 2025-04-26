package com.xenon.core.domain.request.healthAuthorization;

import com.xenon.data.entity.alert.AlertSeverity;
import com.xenon.data.entity.alert.AlertTable;
import com.xenon.data.entity.healthAuthorization.HealthAuthorization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAlertRequest {

    private String title;
    private String description;
    private String alertness;
    private double latitude;
    private double longitude;
    private double radius;
    private AlertSeverity severityLevel = AlertSeverity.MEDIUM;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    public AlertTable toEntity(HealthAuthorization healthAuthorization) {
        AlertTable alert = new AlertTable(
                title,
                description,
                alertness,
                healthAuthorization,
                latitude,
                longitude,
                radius,
                severityLevel
        );

        if (startDate != null) {
            alert.setStartDate(startDate);
        }

        if (endDate != null) {
            alert.setEndDate(endDate);
        }

        return alert;
    }
}