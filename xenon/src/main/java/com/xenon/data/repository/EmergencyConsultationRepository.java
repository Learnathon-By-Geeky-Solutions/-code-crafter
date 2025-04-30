/*
package com.xenon.data.repository;

import com.xenon.data.entity.consultation.emergency.EmergencyConsultation;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.doctor.SpecialistCategory;
import com.xenon.data.entity.hospital.AVAILABILITY;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmergencyConsultationRepository extends JpaRepository<EmergencyConsultation, Long> {

    List<EmergencyConsultation> findByAvailability(AVAILABILITY availability);

    List<EmergencyConsultation> findByAvailabilityAndDoctor_SpecialistCategory(AVAILABILITY availability, SpecialistCategory specialistCategory);

    Optional<EmergencyConsultation> findByDoctor(Doctor doctor);

    boolean existsByDoctor(Doctor doctor);
}*/
