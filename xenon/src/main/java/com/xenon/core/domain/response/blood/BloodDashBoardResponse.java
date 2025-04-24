package com.xenon.core.domain.response.blood;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodDashBoardResponse {

    private Long totalDonor;
    private Long totalDonation;
    private Long getTotalPost;
    private List<BloodRequestPostResponse> bloodRequestPosts;

}
