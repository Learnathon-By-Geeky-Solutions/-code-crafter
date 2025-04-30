package com.xenon.core.domain.response.pharmacy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PharmacyResponse {
    private Long id;
    private String pharmacyName;
    private String phone;
    private String email;
    private String area;
    private String upazilaName;
    private String districtName;
    private String divisionName;
    private Double latitude;
    private Double longitude;
    private String tradeLicenseNumber;
}