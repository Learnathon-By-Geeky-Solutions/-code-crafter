package com.xenon.core.domain.request.ambulance;

import com.xenon.data.entity.ambulance.AmbulanceBookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingStatusRequest {
    private Long bookingId;
    private AmbulanceBookingStatus status;
}