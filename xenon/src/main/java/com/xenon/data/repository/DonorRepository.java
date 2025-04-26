package com.xenon.data.repository;

import com.xenon.data.entity.donor.BloodType;
import com.xenon.data.entity.donor.Donor;
import com.xenon.data.entity.donor.Interested;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    Optional<Donor> findByUserId(Long userId);

    boolean existsByUserId(Long id);

    @Query("SELECT d FROM Donor d WHERE d.bloodType = :bloodType AND d.interested = 'YES'")
    Page<Donor> findByBloodTypeAndInterestedPage(@Param("bloodType") BloodType bloodType, Pageable pageable);

    @Query("SELECT d FROM Donor d WHERE d.interested = 'YES'")
    Page<Donor> findByInterestedPage(Pageable pageable);

    @Query("SELECT d FROM Donor d JOIN d.user u JOIN u.upazila up " +
            "WHERE up.id = :upazilaId AND d.interested = 'YES'")
    Page<Donor> findByUpazilaAndInterestedPage(@Param("upazilaId") Long upazilaId, Pageable pageable);

    @Query("SELECT d FROM Donor d JOIN d.user u JOIN u.upazila up " +
            "WHERE d.bloodType = :bloodType AND up.id = :upazilaId AND d.interested = 'YES'")
    Page<Donor> findByBloodTypeAndUpazilaAndInterestedPage(
            @Param("bloodType") BloodType bloodType,
            @Param("upazilaId") Long upazilaId,
            Pageable pageable);
}
