package com.xenon.core.domain.request.consultation;

import com.xenon.data.entity.consultation.EmergencyConsultation;
import com.xenon.data.entity.consultation.EmergencyConsultationAppointmentTable;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmergencyConsultationTableRequest {

    private Long emergencyConsultation;

    public EmergencyConsultationAppointmentTable toEntity(User user, EmergencyConsultation emergencyConsultation) {
        return new EmergencyConsultationAppointmentTable(user, emergencyConsultation);
    }
}
