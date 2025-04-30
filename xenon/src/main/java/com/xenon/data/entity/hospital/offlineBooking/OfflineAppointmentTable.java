package com.xenon.data.entity.hospital.offlineBooking;

import com.xenon.data.entity.common.BaseAppointment;
import com.xenon.data.entity.hospital.DoctorSchedule;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "offline_appointment_tables")
@Getter
@Setter
@NoArgsConstructor
public class OfflineAppointmentTable extends BaseAppointment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_schedule_id", nullable = false)
    private DoctorSchedule doctorSchedule;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime appointmentTime;

    public OfflineAppointmentTable(User user, DoctorSchedule doctorSchedule) {
        super(user);
        this.doctorSchedule = doctorSchedule;
    }
}