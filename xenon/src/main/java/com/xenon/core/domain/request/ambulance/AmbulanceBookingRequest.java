package com.xenon.core.domain.request.ambulance;

import com.xenon.data.entity.ambulance.Ambulance;
import com.xenon.data.entity.ambulance.AmbulanceBooking;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmbulanceBookingRequest {
    private Long ambulanceId;

    public AmbulanceBooking toEntity(User user, Ambulance ambulance) {
        return new AmbulanceBooking(user, ambulance);
    }
}