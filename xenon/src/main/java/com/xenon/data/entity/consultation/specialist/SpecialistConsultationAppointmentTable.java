package com.xenon.data.entity.consultation.specialist;

import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.user.Gender;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "specialist_appointment_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialistConsultationAppointmentTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialist_consultation_id")
    private SpecialistConsultation specialistConsultation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private AppointmentStatus appointmentStatus;

    @Column
    private String meetingLink;

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
    private LocalDateTime consultationDate;

    @Column
    private LocalTime consultationSlotStart;

    @Column
    private LocalTime consultationSlotEnd;

    public SpecialistConsultationAppointmentTable(SpecialistConsultation specialistConsultation, User user, AppointmentStatus status) {
        this.specialistConsultation = specialistConsultation;
        this.user = user;
        this.appointmentStatus = status != null ? status : AppointmentStatus.PENDING;
        this.isBeneficiary = false;
    }
}