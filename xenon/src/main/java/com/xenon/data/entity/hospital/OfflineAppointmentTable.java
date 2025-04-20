package com.xenon.data.entity.hospital;

import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private APPOINTMENT_STATUS appointment_status;

    public OfflineAppointmentTable(User user, DoctorSchedule doctorSchedule) {
        this.user = user;
        this.doctorSchedule = doctorSchedule;
        this.appointment_status = APPOINTMENT_STATUS.CONFIRMED;
    }

}
