package com.xenon.data.entity.hospital;

import com.xenon.data.entity.location.Upazila;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "hospital_branch")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "HOSPITAL_ID")
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UPAZILA_ID")
    private Upazila upazila;

    @Column(length = 100, nullable = false, unique = true)
    private String branchName;

    @Column(nullable = false)
    private String address;

    @Column(length = 20, nullable = false, unique = true)
    private String phone;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Integer doctors;

    @Column(nullable = false)
    private Integer beds;

    @Column(nullable = false)
    private LocalDate establish_date;

    public HospitalBranch(Hospital hospital, Upazila upazila, String branchName, String address, String phone, String email, Integer doctor, Integer bed, LocalDate establishDate) {
        this.hospital = hospital;
        this.upazila = upazila;
        this.branchName = branchName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.doctors = doctor;
        this.beds = bed;
        this.establish_date = establishDate;
    }
}
