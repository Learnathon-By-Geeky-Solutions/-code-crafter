package com.xenon.core.domain.request.donor;

import com.xenon.data.entity.donor.BloodGiven;
import com.xenon.data.entity.donor.Donor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodGivenInfoRequest {

    private String patientName;
    private Integer quantity;
    private String hospitalName;
    private LocalDate lastDonation;

    public BloodGiven toEntity(Donor donor) {
        return new BloodGiven(donor, patientName, quantity, hospitalName, lastDonation);
    }
}
