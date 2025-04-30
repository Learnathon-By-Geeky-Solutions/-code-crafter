package com.xenon.core.domain.response.ambulance;

import com.xenon.core.domain.response.user.UserResponse;
import com.xenon.data.entity.ambulance.AmbulanceBookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmbulanceBookingResponse {
    private Long id;
    private UserResponse user;
    private AmbulanceResponse ambulance;
    private LocalDateTime bookingTime;
    private AmbulanceBookingStatus status;
}