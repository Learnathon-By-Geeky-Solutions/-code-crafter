package com.xenon.core.domain.response.ambulance;

import com.xenon.core.domain.response.PageResponseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmbulanceListResponse {
    private int ambulanceCount;
    private int doctorCount;
    private PageResponseRequest<AmbulanceResponse> ambulances;
}