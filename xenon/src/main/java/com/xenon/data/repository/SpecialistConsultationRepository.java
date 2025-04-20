package com.xenon.data.repository;

import com.xenon.data.entity.consultation.SpecialistConsultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistConsultationRepository extends JpaRepository<SpecialistConsultation, Long> {
}
