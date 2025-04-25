package com.xenon.core.domain.request.consultation.emergency;

import com.xenon.data.entity.consultation.emergency.EmergencyConsultation;
import com.xenon.data.entity.consultation.emergency.EmergencyConsultationAppointmentTable;
import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.user.Gender;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyConsultationAppointmentRequest {
    private Long emergencyConsultationId;
    private Boolean isBeneficiary;
    private String beneficiaryName;
    private String beneficiaryPhone;
    private String beneficiaryAddress;
    private Gender beneficiaryGender;
    private Integer beneficiaryAge;
    private String medicalHistoryFile;

    public EmergencyConsultationAppointmentTable toEntity(User user, EmergencyConsultation emergencyConsultation) {
        EmergencyConsultationAppointmentTable appointment = new EmergencyConsultationAppointmentTable();
        appointment.setUser(user);
        appointment.setEmergencyConsultation(emergencyConsultation);
        appointment.setAppointmentStatus(AppointmentStatus.PENDING);
        appointment.setIsBeneficiary(isBeneficiary);
        appointment.setBeneficiaryName(beneficiaryName);
        appointment.setBeneficiaryPhone(beneficiaryPhone);
        appointment.setBeneficiaryAddress(beneficiaryAddress);
        appointment.setBeneficiaryGender(beneficiaryGender);
        appointment.setBeneficiaryAge(beneficiaryAge);
        appointment.setMedicalHistoryFile(medicalHistoryFile);
        return appointment;
    }
}