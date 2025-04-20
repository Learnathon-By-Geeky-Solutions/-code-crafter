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

    @Column(length = 30, nullable = false, unique = true)
    private String drugLicenseNumber;

    @Column(length = 30, name = "owner_nid", unique = true)
    private String ownerNID;

    public Pharmacy(String tradeLicenseNumber, String drugLicenseNumber, String ownerNID, User user) {
        this.tradeLicenseNumber = tradeLicenseNumber;
        this.drugLicenseNumber = drugLicenseNumber;
        this.ownerNID = ownerNID;
        this.user = user;
    }
}
