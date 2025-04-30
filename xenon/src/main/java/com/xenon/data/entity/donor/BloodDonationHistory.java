package com.xenon.data.entity.donor;

import com.xenon.core.domain.response.donor.BloodDonationHistoryResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "blood_donation_history")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BloodDonationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DONOR_ID")
    private Donor donor;

    @Column(nullable = false, length = 100)
    private String patientName;

    @Column(nullable = false, length = 10)
    private Integer quantity;

    @Column(nullable = false, length = 100)
    private String hospitalName;

    @Column(name = "last_donation", nullable = false)
    private LocalDate lastDonation;

    public BloodDonationHistory(Donor donor, String patientName, Integer quantity, String hospitalName, LocalDate lastDonation) {

        this.patientName = patientName;
        this.donor = donor;
        this.quantity = quantity;
        this.hospitalName = hospitalName;
        this.lastDonation = lastDonation;
    }

    public BloodDonationHistoryResponse toResponse() {
        return new BloodDonationHistoryResponse(patientName, quantity, hospitalName, lastDonation);
    }


}
