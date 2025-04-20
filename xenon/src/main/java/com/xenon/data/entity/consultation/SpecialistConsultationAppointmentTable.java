package com.xenon.data.entity.consultation;

import com.xenon.data.entity.hospital.APPOINTMENT_STATUS;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private APPOINTMENT_STATUS appointment_status;

    public SpecialistConsultationAppointmentTable(SpecialistConsultation specialistConsultation, User user, APPOINTMENT_STATUS status) {
        this.specialistConsultation = specialistConsultation;
        this.user = user;
        this.appointment_status = APPOINTMENT_STATUS.CONFIRMED;
    }
}
