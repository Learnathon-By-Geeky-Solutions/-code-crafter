package com.xenon.data.repository;

import com.xenon.data.entity.blood.BloodResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodResponseRepository extends JpaRepository<BloodResponse, Long> {
}