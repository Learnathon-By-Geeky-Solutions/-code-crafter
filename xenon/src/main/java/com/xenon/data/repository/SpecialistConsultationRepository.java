/*
package com.xenon.data.repository;

import com.xenon.data.entity.consultation.STATUS;
import com.xenon.data.entity.consultation.specialist.SpecialistConsultation;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.hospital.DAY;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialistConsultationRepository extends JpaRepository<SpecialistConsultation, Long> {

    List<SpecialistConsultation> findByDoctor(Doctor doctor);

    List<SpecialistConsultation> findByDoctorAndStatus(Doctor doctor, STATUS status);

    List<SpecialistConsultation> findByDoctorAndDayAndStatus(Doctor doctor, DAY dayDate, STATUS status);

    List<SpecialistConsultation> findByDoctorIdAndDay(Long doctorId, DAY dayDate);
}*/
