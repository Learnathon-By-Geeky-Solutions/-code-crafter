package com.xenon.core.domain.request.hospital.doctorBooking;

import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.hospital.DoctorSchedule;
import com.xenon.data.entity.hospital.offlineBooking.OfflineAppointmentTable;
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
public class OfflineAppointmentRequest {
    private Long doctorScheduleId;
    private Boolean isBeneficiary;
    private String beneficiaryName;
    private String beneficiaryPhone;
    private String beneficiaryAddress;
    private Gender beneficiaryGender;
    private Integer beneficiaryAge;
    private String medicalHistoryFile;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    public OfflineAppointmentTable toEntity(User user, DoctorSchedule doctorSchedule) {
        OfflineAppointmentTable appointment = new OfflineAppointmentTable();
        appointment.setUser(user);
        appointment.setDoctorSchedule(doctorSchedule);
        appointment.setAppointmentStatus(AppointmentStatus.PENDING);
        appointment.setIsBeneficiary(isBeneficiary);
        appointment.setBeneficiaryName(beneficiaryName);
        appointment.setBeneficiaryPhone(beneficiaryPhone);
        appointment.setBeneficiaryAddress(beneficiaryAddress);
        appointment.setBeneficiaryGender(beneficiaryGender);
        appointment.setBeneficiaryAge(beneficiaryAge);
        appointment.setMedicalHistoryFile(medicalHistoryFile);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setAppointmentTime(appointmentTime);
        return appointment;
    }
}