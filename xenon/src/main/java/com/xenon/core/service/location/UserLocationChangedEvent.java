package com.xenon.core.service.location;

import lombok.Getter;

/**
 * Event that is fired when a user's location has significantly changed
 */
@Getter
public class UserLocationChangedEvent {
    private final Long userId;
    private final double latitude;
    private final double longitude;

    public UserLocationChangedEvent(Long userId, double latitude, double longitude) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}