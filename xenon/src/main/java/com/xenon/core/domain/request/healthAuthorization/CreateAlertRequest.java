package com.xenon.core.domain.request.healthAuthorization;

import com.xenon.data.entity.healthAuthorization.AlertTable;
import com.xenon.data.entity.healthAuthorization.HealthAuthorization;
import com.xenon.data.entity.location.Upazila;
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
    private Long upazilaId;

    public AlertTable toEntity(HealthAuthorization healthAuthorization, Upazila upazila) {
        return new AlertTable(title, description, alertness, upazila, healthAuthorization);
    }

}
