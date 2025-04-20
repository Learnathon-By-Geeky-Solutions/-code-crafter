package com.xenon.data.repository;

import com.xenon.data.entity.ambulance.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmbulanceRepository extends JpaRepository<Ambulance, Long> {
    boolean existsByAmbulanceNumber(String ambulanceNumber);
}
