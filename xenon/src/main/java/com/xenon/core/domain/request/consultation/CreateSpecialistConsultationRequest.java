package com.xenon.core.domain.request.consultation;

import com.xenon.data.entity.consultation.STATUS;
import com.xenon.data.entity.consultation.SpecialistConsultation;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.hospital.AVAILABILITY;
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
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer duration;
    private Integer fee;

    public SpecialistConsultation toEntity(Doctor doctor) {
        return new SpecialistConsultation(doctor, status, availability, date, startTime, endTime, duration, fee);

    }
}
