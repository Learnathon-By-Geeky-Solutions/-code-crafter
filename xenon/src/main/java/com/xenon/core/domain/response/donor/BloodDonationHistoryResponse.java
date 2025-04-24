package com.xenon.core.domain.response.donor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodDonationHistoryResponse {
    private String patientName;
    private Integer quantity;
    private String hospitalName;
    private LocalDate lastDonation;
}
