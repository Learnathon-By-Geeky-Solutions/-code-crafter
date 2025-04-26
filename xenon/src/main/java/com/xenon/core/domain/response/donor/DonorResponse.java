package com.xenon.core.domain.response.donor;

import com.xenon.data.entity.donor.BloodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonorResponse {
    private Long id;
    private String fullName;
    private String phone;
    private BloodType bloodType;
    private String upazila;
    private String area;
    private Boolean isAvailable;
}