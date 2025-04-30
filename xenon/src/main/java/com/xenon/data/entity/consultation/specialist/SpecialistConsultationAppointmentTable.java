package com.xenon.data.entity.consultation.specialist;

import com.xenon.data.entity.common.BaseAppointment;
import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "specialist_appointment_table")
@Getter
@Setter
@NoArgsConstructor
public class SpecialistConsultationAppointmentTable extends BaseAppointment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_consultation_id", nullable = false)
    private SpecialistConsultation specialistConsultation;

    @Column(nullable = false)
    private LocalDateTime consultationDate;

    @Column(nullable = false)
    private LocalTime consultationSlotStart;

    @Column(nullable = false)
    private LocalTime consultationSlotEnd;

    private String meetingLink;

    public SpecialistConsultationAppointmentTable(SpecialistConsultation specialistConsultation, User user) {
        super(user);
        this.specialistConsultation = specialistConsultation;
    }

    public SpecialistConsultationAppointmentTable(SpecialistConsultation specialistConsultation, User user,
                                                  AppointmentStatus status) {
        super(user);
        this.specialistConsultation = specialistConsultation;
        this.setAppointmentStatus(status);
    }
}