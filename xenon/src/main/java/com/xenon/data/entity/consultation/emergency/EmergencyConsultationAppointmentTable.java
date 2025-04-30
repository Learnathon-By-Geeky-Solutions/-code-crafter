package com.xenon.data.entity.consultation.emergency;

import com.xenon.data.entity.common.BaseAppointment;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "emergency_appointment_table")
@Getter
@Setter
@NoArgsConstructor
public class EmergencyConsultationAppointmentTable extends BaseAppointment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emergency_consultation_id", nullable = false)
    private EmergencyConsultation emergencyConsultation;

    @Column(nullable = false)
    private LocalDateTime consultationDate;

    private String meetingLink;

    public EmergencyConsultationAppointmentTable(User user, EmergencyConsultation emergencyConsultation) {
        super(user);
        this.emergencyConsultation = emergencyConsultation;
        this.consultationDate = LocalDateTime.now();
    }
}