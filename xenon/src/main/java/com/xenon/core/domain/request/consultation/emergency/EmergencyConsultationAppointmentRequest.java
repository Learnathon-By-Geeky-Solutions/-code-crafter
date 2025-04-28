package com.xenon.core.domain.request.consultation.emergency;

import com.xenon.core.domain.request.common.BeneficiaryRequest;
import com.xenon.data.entity.consultation.emergency.EmergencyConsultation;
import com.xenon.data.entity.consultation.emergency.EmergencyConsultationAppointmentTable;
import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyConsultationAppointmentRequest extends BeneficiaryRequest {

    private Long emergencyConsultationId;

    public EmergencyConsultationAppointmentTable toEntity(User user, EmergencyConsultation emergencyConsultation) {
        // Create the appointment with user and emergency consultation
        EmergencyConsultationAppointmentTable appointment = new EmergencyConsultationAppointmentTable(user, emergencyConsultation);

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
}