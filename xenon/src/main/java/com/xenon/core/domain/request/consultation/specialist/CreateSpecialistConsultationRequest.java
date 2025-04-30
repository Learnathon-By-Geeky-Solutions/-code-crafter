/*
package com.xenon.core.domain.request.consultation.specialist;

import com.xenon.data.entity.consultation.STATUS;
import com.xenon.data.entity.consultation.specialist.SpecialistConsultation;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.hospital.AVAILABILITY;
import com.xenon.data.entity.hospital.DAY;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSpecialistConsultationRequest {

    private Long doctor;
    private STATUS status;
    private AVAILABILITY availability;
    private DAY date_day;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer duration;
    private Integer fee;

    public SpecialistConsultation toEntity(Doctor doctor) {
        return new SpecialistConsultation(doctor, status, availability, date_day, startTime, endTime, duration, fee);

    }
}
*/
