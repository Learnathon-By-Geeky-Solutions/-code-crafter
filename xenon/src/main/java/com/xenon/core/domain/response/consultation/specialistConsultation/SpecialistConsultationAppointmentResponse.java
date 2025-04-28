package com.xenon.core.domain.response.consultation.specialistConsultation;

import com.xenon.core.domain.response.common.BaseAppointmentResponse;
import com.xenon.core.domain.response.common.DoctorBasicInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistConsultationAppointmentResponse extends BaseAppointmentResponse {
    private Long specialistConsultationId;
    private DoctorBasicInfoResponse doctor;
    private LocalDateTime consultationDate;
    private LocalTime consultationSlotStart;
    private LocalTime consultationSlotEnd;
    private String meetingLink;
}