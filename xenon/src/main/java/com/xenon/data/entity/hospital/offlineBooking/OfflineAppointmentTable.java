package com.xenon.data.entity.hospital.offlineBooking;

import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.hospital.DoctorSchedule;
import com.xenon.data.entity.user.Gender;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "offline_appointment_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfflineAppointmentTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DOCTOR_SCHEDULE_ID")
    private DoctorSchedule doctorSchedule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private AppointmentStatus appointmentStatus;

    @Column
    private String paymentId;

    @Column
    private String paymentStatus;

    @Column
    private Boolean isBeneficiary;

    @Column
    private String beneficiaryName;

    @Column
    private String beneficiaryPhone;

    @Column
    private String beneficiaryAddress;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender beneficiaryGender;

    @Column
    private Integer beneficiaryAge;

    @Column
    private String medicalHistoryFile;

    @Column
    private LocalDate appointmentDate;

    @Column
    private LocalTime appointmentTime;

    public OfflineAppointmentTable(User user, DoctorSchedule doctorSchedule) {
        this.user = user;
        this.doctorSchedule = doctorSchedule;
        this.appointmentStatus = AppointmentStatus.CONFIRMED;
        this.isBeneficiary = false;
    }
}