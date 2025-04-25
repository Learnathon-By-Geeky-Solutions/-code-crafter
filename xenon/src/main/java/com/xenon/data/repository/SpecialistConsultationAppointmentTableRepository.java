package com.xenon.data.repository;

import com.xenon.data.entity.consultation.specialist.SpecialistConsultation;
import com.xenon.data.entity.consultation.specialist.SpecialistConsultationAppointmentTable;
import com.xenon.data.entity.hospital.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SpecialistConsultationAppointmentTableRepository extends JpaRepository<SpecialistConsultationAppointmentTable, Long> {

    List<SpecialistConsultationAppointmentTable> findByUserIdOrderByConsultationDateDesc(Long userId);

    List<SpecialistConsultationAppointmentTable> findBySpecialistConsultationOrderByConsultationDateDesc(SpecialistConsultation specialistConsultation);

    List<SpecialistConsultationAppointmentTable> findBySpecialistConsultationIdAndConsultationDate(Long specialistConsultationId, LocalDate consultationDate);

    boolean existsBySpecialistConsultationIdAndConsultationDateAndConsultationSlotStartAndConsultationSlotEndAndAppointmentStatusNot(
            Long specialistConsultationId,
            LocalDate consultationDate,
            LocalTime slotStartTime,
            LocalTime slotEndTime,
            AppointmentStatus notStatus);
}