package com.xenon.data.entity.bloodBank;

import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BLOOD_BANK")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BloodBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(length = 30, nullable = false, unique = true)
    private String registrationNumber;

    public BloodBank(String registrationNumber, User user) {
        this.registrationNumber = registrationNumber;
        this.user = user;
    }
}
