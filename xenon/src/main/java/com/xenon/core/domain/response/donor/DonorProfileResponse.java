package com.xenon.core.domain.response.donor;

import com.xenon.core.domain.response.user.UserResponse;
import com.xenon.data.entity.donor.BloodType;
import com.xenon.data.entity.donor.Interested;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonorProfileResponse {
    private Long id;
    private UserResponse user;
    private BloodType bloodType;
    private Integer age;
    private Integer weight;
    private Interested interested;
    private LocalDate lastDonationDate;
    private Boolean canDonate;
}