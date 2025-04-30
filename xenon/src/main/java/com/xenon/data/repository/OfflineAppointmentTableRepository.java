package com.xenon.data.repository;

import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.hospital.DoctorSchedule;
import com.xenon.data.entity.hospital.offlineBooking.OfflineAppointmentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface OfflineAppointmentTableRepository extends JpaRepository<OfflineAppointmentTable, Long> {

    List<OfflineAppointmentTable> findByUserIdOrderByAppointmentDateDescAppointmentTimeDesc(Long userId);

    List<OfflineAppointmentTable> findByDoctorSchedule_OfflineDoctorAffiliation_HospitalBranchIdOrderByAppointmentDateDescAppointmentTimeDesc(Long hospitalBranchId);

    List<OfflineAppointmentTable> findByDoctorSchedule_OfflineDoctorAffiliation_DoctorIdOrderByAppointmentDateDescAppointmentTimeDesc(Long doctorId);

    int countByDoctorScheduleAndAppointmentDateAndAppointmentTimeBetweenAndAppointmentStatusNot(
            DoctorSchedule doctorSchedule,
            LocalDate appointmentDate,
            LocalTime startTime,
            LocalTime endTime,
            AppointmentStatus notStatus);
}