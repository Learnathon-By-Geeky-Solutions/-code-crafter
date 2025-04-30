package com.xenon.core.domain.response.bloodBank;

import com.xenon.core.domain.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankResponse {
    private Long id;
    private String registrationNumber;
    private UserResponse user;
    private Double latitude;
    private Double longitude;
}