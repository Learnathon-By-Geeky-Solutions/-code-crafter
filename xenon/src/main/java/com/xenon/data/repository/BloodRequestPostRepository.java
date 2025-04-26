package com.xenon.data.repository;

import com.xenon.core.domain.response.blood.projection.BloodMetaDataProjection;
import com.xenon.data.entity.blood.BloodRequestPost;
import com.xenon.data.entity.donor.BloodType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodRequestPostRepository extends JpaRepository<BloodRequestPost, Long> {

    @Query(value = """
        SELECT 
            (SELECT COUNT(*) FROM donor) AS totalDonor,
            (SELECT COUNT(*) FROM blood_request_post) AS totalPost,
            (SELECT COALESCE(SUM(quantity), 0) FROM blood_donation_history) AS totalDonation
        """, nativeQuery = true)
    BloodMetaDataProjection getBloodMetadata();

    Page<BloodRequestPost> findAllByUser_Id(Long id, Pageable pageable);

    Page<BloodRequestPost> findAllByBloodType(BloodType bloodType, Pageable pageable);

    Page<BloodRequestPost> findAllByUpazila_Id(Long upazilaId, Pageable pageable);

    Page<BloodRequestPost> findAllByBloodTypeAndUpazila_Id(BloodType bloodType, Long upazilaId, Pageable pageable);

    Page<BloodRequestPost> findAllByOrderByDateDesc(Pageable pageable);

    @Query("SELECT b FROM BloodRequestPost b WHERE b.date >= CURRENT_DATE ORDER BY b.date ASC")
    Page<BloodRequestPost> findUpcomingRequests(Pageable pageable);

    @Query(value = "SELECT * FROM blood_request_post ORDER BY date DESC LIMIT :limit", nativeQuery = true)
    List<BloodRequestPost> findRecentPosts(@Param("limit") int limit);
}
