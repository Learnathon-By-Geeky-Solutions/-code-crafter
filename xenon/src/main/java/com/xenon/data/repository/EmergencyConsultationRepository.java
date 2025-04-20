package com.xenon.data.repository;

import com.xenon.data.entity.consultation.EmergencyConsultation;
import com.xenon.data.entity.hospital.AVAILABILITY;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyConsultationRepository  extends JpaRepository<EmergencyConsultation, Long> {
    List<EmergencyConsultation> findAllByAvailability(AVAILABILITY availability);
}
