package com.xenon.core.domain.request.consultation.emergency;

import com.xenon.data.entity.consultation.emergency.EmergencyConsultation;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.hospital.AVAILABILITY;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmergencyConsultationRequest {

    private Long doctorId;
    private AVAILABILITY availability;
    private Integer fee;

    public EmergencyConsultation toEntity(Doctor doctor) {
        return new EmergencyConsultation(doctor, availability, fee);
    }
}
