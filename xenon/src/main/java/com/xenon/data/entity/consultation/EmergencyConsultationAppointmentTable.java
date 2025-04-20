package com.xenon.data.entity.consultation;


import com.xenon.data.entity.hospital.APPOINTMENT_STATUS;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private APPOINTMENT_STATUS appointment_status;

    public EmergencyConsultationAppointmentTable(User user, EmergencyConsultation emergencyConsultation) {
        this.user = user;
        this.emergencyConsultation = emergencyConsultation;
        this.appointment_status = APPOINTMENT_STATUS.CONFIRMED;
    }
}
