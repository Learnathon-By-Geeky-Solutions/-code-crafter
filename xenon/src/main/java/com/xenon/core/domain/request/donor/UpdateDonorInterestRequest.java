package com.xenon.core.domain.request.donor;

import com.xenon.data.entity.donor.Interested;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDonorInterestRequest {
    private Interested interested;
}