package com.xenon.data.repository;

import com.xenon.data.entity.hospital.HospitalBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalBranchRepository extends JpaRepository<HospitalBranch, Long> {
    /*boolean existsByEmail(String email);

    boolean existsByPhone(String phone);*/
}
