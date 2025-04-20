package com.xenon.core.domain.response.emergencyConsultation;

import com.xenon.core.domain.response.doctor.DoctorResponse;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.hospital.AVAILABILITY;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmergencyConsultationResponse {
    private DoctorResponse doctor;
    private AVAILABILITY availability;
    private Integer fee;
}
