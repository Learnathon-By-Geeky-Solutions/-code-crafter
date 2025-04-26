package com.xenon.data.entity.donor;

import com.xenon.core.domain.response.donor.DonorResponse;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "donor")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private BloodType bloodType;

    @Column(nullable = false, length = 10)
    private Integer age;

    @Column(nullable = false, length = 100)
    private Integer weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Interested interested;

    @Column(name = "last_donation")
    private LocalDate lastDonation;


    public Donor(BloodType bloodType, Integer age, Integer weight, Interested interested, User user) {
        this.bloodType = bloodType;
        this.age = age;
        this.weight = weight;
        this.interested = interested;
        this.user = user;
    }

    public DonorResponse toResponse() {
        return new DonorResponse(
                id,
                user.getFirstName() + " " + user.getLastName(),
                user.getPhone(),
                bloodType,
                user.getUpazila() != null ? user.getUpazila().getName() : null,
                user.getArea(),
                isAvailableForDonation()
        );
    }

    public boolean isAvailableForDonation() {
        if (interested != Interested.YES) return false;
        if (lastDonation == null) return true;
        return lastDonation.plusMonths(3).isBefore(LocalDate.now());
    }
}
