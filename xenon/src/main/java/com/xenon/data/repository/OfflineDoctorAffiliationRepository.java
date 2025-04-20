package com.xenon.data.repository;

import com.xenon.data.entity.hospital.DoctorAffiliationId;
import com.xenon.data.entity.hospital.OfflineDoctorAffiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfflineDoctorAffiliationRepository extends JpaRepository<OfflineDoctorAffiliation, DoctorAffiliationId> {

    boolean existsByDoctorIdAndHospitalBranchId(Long doctor, Long hospitalBranch);
}
