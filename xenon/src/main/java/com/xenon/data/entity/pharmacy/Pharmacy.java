package com.xenon.data.entity.pharmacy;

import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pharmacy")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(length = 30, nullable = false, unique = true)
    private String tradeLicenseNumber;

    public Pharmacy(String tradeLicenseNumber, User user) {
        this.tradeLicenseNumber = tradeLicenseNumber;
        this.user = user;
    }
}
