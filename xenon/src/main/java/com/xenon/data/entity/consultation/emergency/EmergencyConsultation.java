package com.xenon.data.entity.consultation.emergency;

import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.hospital.AVAILABILITY;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emergency_consultation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyConsultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AVAILABILITY availability;

    @Column(nullable = false)
    private Integer fee;

    public EmergencyConsultation(Doctor doctor, AVAILABILITY availability, Integer fee) {
        this.doctor = doctor;
        this.availability = availability;
        this.fee = fee;
    }

}