package com.xenon.core.domain.response.ambulance;

import com.xenon.core.domain.response.user.UserResponse;
import com.xenon.data.entity.ambulance.AmbulanceStatus;
import com.xenon.data.entity.ambulance.AmbulanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmbulanceDetailedResponse {
    private Long id;
    private UserResponse user;
    private AmbulanceType ambulanceType;
    private String ambulanceNumber;
    private AmbulanceStatus ambulanceStatus;
    private String about;
    private List<String> serviceOffers;
    private String hospitalAffiliation;
    private List<String> coverageAreas;
    private Integer responseTime;
    private Integer doctors;
    private Integer nurses;
    private Integer paramedics;
    private List<String> teamQualification;
    private Integer startingFee;
    private Double averageRating;
    private Integer totalReviews;
}