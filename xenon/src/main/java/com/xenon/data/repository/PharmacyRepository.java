package com.xenon.data.repository;

import com.xenon.data.entity.pharmacy.Pharmacy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    boolean existsByTradeLicenseNumber(String tradeLicenseNumber);

    // Find pharmacies by user's name
    @Query("SELECT p FROM Pharmacy p JOIN p.user u WHERE " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Pharmacy> findByUserName(@Param("name") String name, Pageable pageable);

    // Find pharmacies by upazila ID
    @Query("SELECT p FROM Pharmacy p JOIN p.user u WHERE u.upazila.id = :upazilaId")
    Page<Pharmacy> findByUpazilaId(@Param("upazilaId") Long upazilaId, Pageable pageable);
}