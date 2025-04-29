/*
// Base response for all appointments
package com.xenon.core.domain.response.common;

import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseAppointmentResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String userPhone;
    private AppointmentStatus appointmentStatus;
    private Boolean isBeneficiary;
    private String beneficiaryName;
    private String beneficiaryPhone;
    private String beneficiaryAddress;
    private Gender beneficiaryGender;
    private Integer beneficiaryAge;
    private String medicalHistoryFile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}*/
