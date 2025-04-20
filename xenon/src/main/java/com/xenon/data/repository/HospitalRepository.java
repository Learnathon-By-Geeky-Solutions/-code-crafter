package com.xenon.data.repository;

import com.xenon.data.entity.hospital.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    boolean existsByRegistrationNo(String registrationNo);

    Optional<Hospital>  findByUserId(Long id);
}
