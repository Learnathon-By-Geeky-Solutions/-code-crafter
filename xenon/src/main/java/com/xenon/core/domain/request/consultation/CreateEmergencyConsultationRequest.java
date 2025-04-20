package com.xenon.core.domain.request.consultation;

import com.xenon.data.entity.consultation.EmergencyConsultation;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.hospital.AVAILABILITY;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmergencyConsultationRequest {

    private Long doctor;
    private AVAILABILITY availability;
    private Integer fee;

    public EmergencyConsultation toEntity(Doctor doctor) {
        return new EmergencyConsultation(doctor, availability, fee);
    }
}
