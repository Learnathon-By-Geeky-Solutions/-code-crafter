package com.xenon.core.domain.response.donor;

import com.xenon.data.entity.donor.BloodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodDonationHistoryListResponse {

    private Long donationCount;
    private Long unitCount;
    private BloodType bloodType;
    private List<BloodDonationHistoryResponse> bloodDonationHistoryResponses;
}
