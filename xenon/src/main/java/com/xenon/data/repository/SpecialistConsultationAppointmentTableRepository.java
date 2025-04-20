package com.xenon.data.repository;

import com.xenon.data.entity.consultation.SpecialistConsultationAppointmentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistConsultationAppointmentTableRepository extends JpaRepository<SpecialistConsultationAppointmentTable, Long> {
}
