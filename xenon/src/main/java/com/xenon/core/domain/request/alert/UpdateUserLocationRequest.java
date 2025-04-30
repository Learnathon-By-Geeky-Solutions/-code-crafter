package com.xenon.core.domain.request.alert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserLocationRequest {
    private Double latitude;
    private Double longitude;
    private Boolean locationAllowed;
}