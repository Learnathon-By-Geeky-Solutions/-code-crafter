package com.xenon.data.repository;

import com.xenon.data.entity.ambulance.AmbulanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbulanceReviewRepository extends JpaRepository<AmbulanceReview, Long> {
    List<AmbulanceReview> findAllByAmbulance_Id(Long id);
}
