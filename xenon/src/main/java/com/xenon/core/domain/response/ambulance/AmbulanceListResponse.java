package com.xenon.core.domain.response.ambulance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmbulanceListResponse {
    private int ambulanceCount;
    private int doctorCount;
    private List<AmbulanceResponse> ambulances;
}
