package com.xenon.data.entity.hospital;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "doctor_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id"),
            @JoinColumn(name = "hospital_branch_id", referencedColumnName = "hospital_branch_id")
    })
    OfflineDoctorAffiliation offlineDoctorAffiliation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private DAY day;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AVAILABILITY availability;

    @Column(nullable = false)
    private Integer booking_quantity;

    private Integer duration;


    public DoctorSchedule(OfflineDoctorAffiliation offlineDoctorAffiliation, DAY day, LocalTime startTime, LocalTime endTime, AVAILABILITY availability, Integer bookingQuantity, Integer duration) {
        this.offlineDoctorAffiliation = offlineDoctorAffiliation;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availability = availability;
        this.booking_quantity = bookingQuantity;
        this.duration = duration;
    }
}
