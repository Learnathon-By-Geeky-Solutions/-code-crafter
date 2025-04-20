package com.xenon.data.repository;

import com.xenon.data.entity.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    boolean existsByTradeLicenseNumber(String tradeLicenseNumber);

    boolean existsByDrugLicenseNumber(String drugLicenseNumber);

    boolean existsByOwnerNID(String ownerNID);
}
