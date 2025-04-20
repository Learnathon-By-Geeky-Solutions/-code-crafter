package com.xenon.data.repository;

import com.xenon.data.entity.ambulance.AmbulanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmbulanceReviewRepository extends JpaRepository<AmbulanceReview, Long> {
}
