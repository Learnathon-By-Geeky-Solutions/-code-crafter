package com.xenon.core.domain.request.hospital;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xenon.data.entity.hospital.AVAILABILITY;
import com.xenon.data.entity.hospital.DAY;
import com.xenon.data.entity.hospital.DoctorSchedule;
import com.xenon.data.entity.hospital.OfflineDoctorAffiliation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorScheduleRequest {

    private Long doctorId;
    private Long hospitalBranchId;
    private DAY day;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime ;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime  endTime ;
    private AVAILABILITY availability;
    private Integer booking_quantity;
    private Integer duration;

    public DoctorSchedule toEntity(OfflineDoctorAffiliation offlineDoctorAffiliation) {
        return new DoctorSchedule(offlineDoctorAffiliation, day, startTime, endTime, availability, booking_quantity, duration);
    }
}
