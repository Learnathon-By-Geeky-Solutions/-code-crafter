package com.xenon.core.domain.response.donor.projection;

import com.xenon.data.entity.donor.BloodType;

public interface BloodDonationHistoryMetaData {
    Long getTotalDonation();
    Long getTotalUnit();
    BloodType getBloodType();
}
