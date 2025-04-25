package com.xenon.core.domain.request.healthAuthorization;

import com.xenon.data.entity.healthAuthorization.AlertTable;
import com.xenon.data.entity.healthAuthorization.HealthAuthorization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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

    public AlertTable toEntity(HealthAuthorization healthAuthorization) {
        return new AlertTable(title, description, alertness, healthAuthorization, latitude, longitude, radius);
    }

}
