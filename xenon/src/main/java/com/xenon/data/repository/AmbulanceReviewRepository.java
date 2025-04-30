package com.xenon.data.repository;

import com.xenon.data.entity.ambulance.AmbulanceReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbulanceReviewRepository extends JpaRepository<AmbulanceReview, Long> {
    List<AmbulanceReview> findAllByAmbulance_Id(Long id);

    Page<AmbulanceReview> findAllByAmbulance_Id(Long id, Pageable pageable);

    @Query("SELECT AVG(ar.rating) FROM AmbulanceReview ar WHERE ar.ambulance.id = :ambulanceId")
    Double getAverageRatingByAmbulanceId(@Param("ambulanceId") Long ambulanceId);

    @Query("SELECT COUNT(ar) FROM AmbulanceReview ar WHERE ar.ambulance.id = :ambulanceId")
    Integer getReviewCountByAmbulanceId(@Param("ambulanceId") Long ambulanceId);

}