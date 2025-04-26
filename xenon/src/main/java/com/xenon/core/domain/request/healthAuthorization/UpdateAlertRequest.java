package com.xenon.core.domain.request.healthAuthorization;

import com.xenon.data.entity.alert.AlertSeverity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAlertRequest {

    private String title;
    private String description;
    private String alertness;
    private Double latitude;
    private Double longitude;
    private Double radius;
    private AlertSeverity severityLevel;
    private Boolean isActive;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}