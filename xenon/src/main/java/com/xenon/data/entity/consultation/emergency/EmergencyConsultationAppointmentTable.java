package com.xenon.data.entity.consultation.emergency;

import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.user.Gender;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "emergency_appointment_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyConsultationAppointmentTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emergency_consultation_id")
    private EmergencyConsultation emergencyConsultation;

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

    public EmergencyConsultationAppointmentTable(User user, EmergencyConsultation emergencyConsultation) {
        this.user = user;
        this.emergencyConsultation = emergencyConsultation;
        this.appointmentStatus = AppointmentStatus.PENDING;
        this.isBeneficiary = false;
    }
}