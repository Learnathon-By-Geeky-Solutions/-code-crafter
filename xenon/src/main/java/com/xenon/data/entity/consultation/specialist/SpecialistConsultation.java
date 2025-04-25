package com.xenon.data.entity.consultation.specialist;

import com.xenon.data.entity.consultation.STATUS;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.hospital.AVAILABILITY;
import com.xenon.data.entity.hospital.DAY;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "specialist_consultation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialistConsultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private STATUS status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AVAILABILITY availability;

    @Column(nullable = false)
    private DAY day;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer fee;



    public SpecialistConsultation(Doctor doctor, STATUS status, AVAILABILITY availability, DAY date, LocalTime startTime, LocalTime endTime, Integer duration, Integer fee) {
        this.doctor = doctor;
        this.status = status;
        this.availability = availability;
        this.day = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.fee = fee;
    }
}
