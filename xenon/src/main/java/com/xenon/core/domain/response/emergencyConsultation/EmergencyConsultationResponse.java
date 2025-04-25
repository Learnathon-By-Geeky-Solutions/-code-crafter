package com.xenon.core.domain.response.emergencyConsultation;

import com.xenon.core.domain.response.doctor.DoctorProfileResponse;
import com.xenon.data.entity.hospital.AVAILABILITY;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmergencyConsultationResponse {
    private DoctorProfileResponse doctor;
    private AVAILABILITY availability;
    private Integer fee;
}
