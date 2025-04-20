package com.xenon.data.repository;

import com.xenon.data.entity.bloodBank.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {

    boolean existsByRegistrationNumber(String registrationNumber);
}
