package com.xenon.core.domain.response.blood;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodRequestPostDisplayResponse {
    int responseCount;
    List<BloodRequestPostResponse> bloodRequestPostResponses;
}
