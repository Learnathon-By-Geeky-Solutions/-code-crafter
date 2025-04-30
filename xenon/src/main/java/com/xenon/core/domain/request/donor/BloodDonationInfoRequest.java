package com.xenon.core.domain.request.donor;

import com.xenon.data.entity.donor.BloodDonationHistory;
import com.xenon.data.entity.donor.Donor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodDonationInfoRequest {

    private String patientName;
    private Integer quantity;
    private String hospitalName;
    private LocalDate lastDonation;

    public BloodDonationHistory toEntity(Donor donor) {
        return new BloodDonationHistory(donor, patientName, quantity, hospitalName, lastDonation);
    }
}
