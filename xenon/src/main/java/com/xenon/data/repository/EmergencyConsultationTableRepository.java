package com.xenon.data.repository;

import com.xenon.data.entity.consultation.EmergencyConsultationAppointmentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyConsultationTableRepository extends JpaRepository<EmergencyConsultationAppointmentTable, Long> {
}
