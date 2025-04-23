package com.xenon.data.repository;

import com.xenon.core.domain.response.blood.projection.BloodMetaDataProjection;
import com.xenon.data.entity.blood.BloodRequestPost;
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

    @Query(value = "SELECT * FROM blood_request_post ORDER BY date DESC LIMIT :limit", nativeQuery = true)
    List<BloodRequestPost> findRecentPosts(@Param("limit") int limit);


    @Query(value = "SELECT COUNT(*) AS responseCount FROM blood_comment_table WHERE blood_request_post_id = :postId", nativeQuery = true)
    int getResponseCount(@Param("postId") Long postId);

    @Query(value = """
            SELECT
               brp.id,
               u.first_name || ' ' || u.last_name AS full_name,
               brp.patient_name,
               brp.blood_type,
               brp.quantity,
               brp.hospital_name,
               upz.name AS upazila_name,
               brp.contact_number,
               brp.date,
               brp.description,
               COUNT(bct.id) AS response_count
            FROM blood_request_post brp
            JOIN "table_user" u ON brp.user_id = u.id
            JOIN upazila upz ON brp.upazila_id = upz.id
            LEFT JOIN blood_comment_table bct ON brp.id = bct.blood_request_post_id
            GROUP BY brp.id, u.first_name, u.last_name, upz.name
            ORDER BY brp.date DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<BloodRequestPost> findRecentPostWithCommentCount(@Param("limit") int limit);

    List<BloodRequestPost> findAllByUser_Id(Long id);
}
