/*
package com.xenon.data.repository;

import com.xenon.data.entity.consultation.emergency.EmergencyConsultation;
import com.xenon.data.entity.consultation.emergency.EmergencyConsultationAppointmentTable;
import com.xenon.data.entity.hospital.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyConsultationTableRepository extends JpaRepository<EmergencyConsultationAppointmentTable, Long> {

    List<EmergencyConsultationAppointmentTable> findByUserIdOrderByConsultationDateDesc(Long userId);

    List<EmergencyConsultationAppointmentTable> findByEmergencyConsultationOrderByConsultationDateDesc(EmergencyConsultation emergencyConsultation);

    List<EmergencyConsultationAppointmentTable> findByEmergencyConsultationAndAppointmentStatus(
            EmergencyConsultation emergencyConsultation, AppointmentStatus status);

}*/
