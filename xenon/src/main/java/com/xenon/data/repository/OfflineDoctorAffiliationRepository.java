package com.xenon.data.repository;

import com.xenon.data.entity.doctor.SpecialistCategory;
import com.xenon.data.entity.hospital.DoctorAffiliationId;
import com.xenon.data.entity.hospital.OfflineDoctorAffiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfflineDoctorAffiliationRepository extends JpaRepository<OfflineDoctorAffiliation, DoctorAffiliationId> {

    /*List<OfflineDoctorAffiliation> findByHospitalBranchId(Long hospitalBranchId);

    List<OfflineDoctorAffiliation> findByDoctorId(Long doctorId);

    List<OfflineDoctorAffiliation> findByHospitalBranchIdAndDoctor_SpecialistCategory(
            Long hospitalBranchId, SpecialistCategory specialistCategory);

    boolean existsByDoctorIdAndHospitalBranchId(Long doctorId, Long hospitalBranchId);*/
}