package com.xenon.core.domain.request.consultation.specialist;

import com.xenon.core.domain.request.common.BeneficiaryRequest;
import com.xenon.data.entity.consultation.specialist.SpecialistConsultation;
import com.xenon.data.entity.consultation.specialist.SpecialistConsultationAppointmentTable;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistConsultationAppointmentRequest extends BeneficiaryRequest {

    private Long specialistConsultationId;
    private LocalDate consultationDate;
    private LocalTime slotStartTime;
    private LocalTime slotEndTime;

    public SpecialistConsultationAppointmentTable toEntity(User user, SpecialistConsultation specialistConsultation) {
        // Create new appointment with the specialist consultation and user
        SpecialistConsultationAppointmentTable appointment = new SpecialistConsultationAppointmentTable(specialistConsultation, user);

        // Set appointment details
        appointment.setConsultationDate(LocalDateTime.of(consultationDate, slotStartTime));
        appointment.setConsultationSlotStart(slotStartTime);
        appointment.setConsultationSlotEnd(slotEndTime);

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