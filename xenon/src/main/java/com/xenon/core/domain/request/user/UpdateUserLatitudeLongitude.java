package com.xenon.core.domain.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserLatitudeLongitude {
    private Double latitude;
    private Double longitude;
}
