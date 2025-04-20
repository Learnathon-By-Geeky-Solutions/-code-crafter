package com.xenon.core.domain.request.consultation;

import com.xenon.data.entity.consultation.SpecialistConsultation;
import com.xenon.data.entity.consultation.SpecialistConsultationAppointmentTable;
import com.xenon.data.entity.hospital.APPOINTMENT_STATUS;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSpecialistConsultationAppointmentTableRequest {

    private Long specialistConsultation;
    private Long user;
    private APPOINTMENT_STATUS status;

    public SpecialistConsultationAppointmentTable toEntity(SpecialistConsultation specialistConsultation, User user) {
        return new SpecialistConsultationAppointmentTable(specialistConsultation, user, status);
    }
}
