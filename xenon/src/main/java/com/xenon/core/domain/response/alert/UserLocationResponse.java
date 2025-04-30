// Response for user location status
package com.xenon.core.domain.response.alert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLocationResponse {
    private Boolean isLocationShared;
    private LocalDateTime lastUpdateTime;
    private Integer activeAlertsCount; // Number of active alerts in user's area
}