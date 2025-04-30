package com.xenon.data.entity.hospital;

import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hospital")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private HOSPITAL_TYPE hospitalType;

    @Column(length = 30, nullable = false, unique = true)
    private String registrationNo;


    public Hospital(String registrationNo, HOSPITAL_TYPE hospitalType, User user) {
        this.registrationNo = registrationNo;
        this.hospitalType = hospitalType;
        this.user = user;
    }
}
