package com.xenon.data.repository;

import com.xenon.data.entity.hospital.DAY;
import com.xenon.data.entity.hospital.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    /*List<DoctorSchedule> findByOfflineDoctorAffiliation_DoctorIdAndDay(Long doctorId, DAY day);

    List<DoctorSchedule> findByOfflineDoctorAffiliation_HospitalBranchIdAndOfflineDoctorAffiliation_DoctorId(
            Long hospitalBranchId, Long doctorId);

    *//*List<DoctorSchedule> findByOfflineDoctorAffiliation_HospitalBranchId(Long hospitalBranchId);

    List<DoctorSchedule> findByOfflineDoctorAffiliation_DoctorId(Long doctorId);*/
}