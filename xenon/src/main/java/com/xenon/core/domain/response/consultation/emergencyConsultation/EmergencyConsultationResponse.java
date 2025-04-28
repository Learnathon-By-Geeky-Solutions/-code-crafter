package com.xenon.core.domain.response.consultation.emergencyConsultation;

import com.xenon.data.entity.hospital.AVAILABILITY;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyConsultationResponse {
    private Long id;
    private Long doctorId;  // For retrieving full profile later
    private String doctorName;
    private String specialistCategory;
    private AVAILABILITY availability;
    private Integer fee;
}