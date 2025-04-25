package com.xenon.core.domain.request.consultation.specialist;

import com.xenon.data.entity.consultation.specialist.SpecialistConsultation;
import com.xenon.data.entity.consultation.specialist.SpecialistConsultationAppointmentTable;
import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.user.Gender;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistConsultationAppointmentRequest {
    private Long specialistConsultationId;
    private Boolean isBeneficiary;
    private String beneficiaryName;
    private String beneficiaryPhone;
    private String beneficiaryAddress;
    private Gender beneficiaryGender;
    private Integer beneficiaryAge;
    private String medicalHistoryFile;
    private LocalDate consultationDate;
    private LocalTime slotStartTime;
    private LocalTime slotEndTime;

    public SpecialistConsultationAppointmentTable toEntity(User user, SpecialistConsultation specialistConsultation) {
        SpecialistConsultationAppointmentTable appointment = new SpecialistConsultationAppointmentTable();
        appointment.setUser(user);
        appointment.setSpecialistConsultation(specialistConsultation);
        appointment.setAppointmentStatus(AppointmentStatus.PENDING);
        appointment.setIsBeneficiary(isBeneficiary);
        appointment.setBeneficiaryName(beneficiaryName);
        appointment.setBeneficiaryPhone(beneficiaryPhone);
        appointment.setBeneficiaryAddress(beneficiaryAddress);
        appointment.setBeneficiaryGender(beneficiaryGender);
        appointment.setBeneficiaryAge(beneficiaryAge);
        appointment.setMedicalHistoryFile(medicalHistoryFile);
        appointment.setConsultationDate(consultationDate.atTime(slotStartTime));
        appointment.setConsultationSlotStart(slotStartTime);
        appointment.setConsultationSlotEnd(slotEndTime);
        return appointment;
    }
}