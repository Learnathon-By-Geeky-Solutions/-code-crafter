package com.xenon.core.domain.response.consultation.emergencyConsultation;

import com.xenon.core.domain.response.common.BaseAppointmentResponse;
import com.xenon.core.domain.response.common.DoctorBasicInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyConsultationAppointmentResponse extends BaseAppointmentResponse {
    private Long emergencyConsultationId;
    private DoctorBasicInfoResponse doctor;
    private LocalDateTime consultationDate;
    private String meetingLink;
}