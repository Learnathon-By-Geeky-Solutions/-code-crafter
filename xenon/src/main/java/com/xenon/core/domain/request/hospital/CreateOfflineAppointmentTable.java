package com.xenon.core.domain.request.hospital;

import com.xenon.data.entity.hospital.DoctorSchedule;
import com.xenon.data.entity.hospital.OfflineAppointmentTable;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOfflineAppointmentTable {

    private Long doctorScheduleId;
    private Long userId;

    public OfflineAppointmentTable toEntity(User user, DoctorSchedule doctorSchedule) {
        return new OfflineAppointmentTable(user, doctorSchedule);
    }
}
