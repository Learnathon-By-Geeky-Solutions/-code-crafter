package com.xenon.core.domain.request.ambulance;

import com.xenon.data.entity.ambulance.Ambulance;
import com.xenon.data.entity.ambulance.AmbulanceStatus;
import com.xenon.data.entity.ambulance.AmbulanceType;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAmbulanceAccountRequest {

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

    public Ambulance toEntity(User user) {
        return new Ambulance(ambulanceType, ambulanceNumber, ambulanceStatus, user, about, service_offers, hospital_affiliation, coverage_areas, response_time, doctors, nurses, paramedics, team_qualification, starting_fee);
    }
}
