package com.xenon.core.domain.request.consultation.specialist;

import com.xenon.data.entity.consultation.specialist.SpecialistConsultation;
import com.xenon.data.entity.consultation.specialist.SpecialistConsultationAppointmentTable;
import com.xenon.data.entity.hospital.AppointmentStatus;
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
    private AppointmentStatus status;

    public SpecialistConsultationAppointmentTable toEntity(SpecialistConsultation specialistConsultation, User user) {
        return new SpecialistConsultationAppointmentTable(specialistConsultation, user, status);
    }
}
