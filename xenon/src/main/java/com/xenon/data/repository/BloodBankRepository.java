package com.xenon.data.repository;

import com.xenon.data.entity.bloodBank.BloodBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
    boolean existsByRegistrationNumber(String registrationNumber);

    @Query("SELECT b FROM BloodBank b JOIN b.user u JOIN u.upazila up WHERE up.id = :upazilaId")
    Page<BloodBank> findByUpazilaId(@Param("upazilaId") Long upazilaId, Pageable pageable);
}