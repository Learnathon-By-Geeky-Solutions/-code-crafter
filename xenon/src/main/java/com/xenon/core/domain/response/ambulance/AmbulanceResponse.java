package com.xenon.core.domain.response.ambulance;

import com.xenon.core.domain.response.user.UserResponse;
import com.xenon.data.entity.ambulance.AmbulanceStatus;
import com.xenon.data.entity.ambulance.AmbulanceType;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmbulanceResponse {
    private Long id;
    private UserResponse user;
    private AmbulanceType ambulanceType;
    private String ambulanceNumber;
    private AmbulanceStatus ambulanceStatus;
    private String about;
    private String service_offers;
    private String hospital_affiliation;
    private String coverage_areas;
    private Integer response_time;
    private Integer doctors;
    private Integer nurses;
    private Integer paramedics;
    private String team_qualification;
    private Integer starting_fee;
}
