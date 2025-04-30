/*
package com.xenon.core.domain.request.hospital.doctorBooking;

import com.xenon.core.domain.request.common.BeneficiaryRequest;
import com.xenon.data.entity.hospital.DoctorSchedule;
import com.xenon.data.entity.hospital.offlineBooking.OfflineAppointmentTable;
import com.xenon.data.entity.user.User;
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
public class OfflineAppointmentRequest extends BeneficiaryRequest {
    private Long doctorScheduleId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    public OfflineAppointmentTable toEntity(User user, DoctorSchedule doctorSchedule) {
        // Create new appointment
        OfflineAppointmentTable appointment = new OfflineAppointmentTable(user, doctorSchedule);

        // Set appointment date and time
        appointment.setAppointmentDate(this.appointmentDate);
        appointment.setAppointmentTime(this.appointmentTime);

        // Set beneficiary information
        appointment.setIsBeneficiary(this.getIsBeneficiary());
        if (Boolean.TRUE.equals(this.getIsBeneficiary())) {
            appointment.setBeneficiaryName(this.getBeneficiaryName());
            appointment.setBeneficiaryPhone(this.getBeneficiaryPhone());
            appointment.setBeneficiaryAddress(this.getBeneficiaryAddress());
            appointment.setBeneficiaryGender(this.getBeneficiaryGender());
            appointment.setBeneficiaryAge(this.getBeneficiaryAge());
        }

        // Set medical history file if provided
        if (this.getMedicalHistoryFile() != null) {
            appointment.setMedicalHistoryFile(this.getMedicalHistoryFile());
        }

        return appointment;
    }
}*/
