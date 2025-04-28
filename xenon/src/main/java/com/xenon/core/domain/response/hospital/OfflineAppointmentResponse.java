package com.xenon.core.domain.response.hospital;

import com.xenon.core.domain.response.common.BaseAppointmentResponse;
import com.xenon.core.domain.response.common.DoctorBasicInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OfflineAppointmentResponse extends BaseAppointmentResponse {
    private Long doctorScheduleId;
    private DoctorBasicInfoResponse doctor;
    private String hospitalName;
    private String branchName;
    private String branchAddress;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
}