package com.xenon.data.repository;

import com.xenon.core.domain.response.donor.BloodDonationHistoryResponse;
import com.xenon.core.domain.response.donor.projection.BloodDonationHistoryMetaData;
import com.xenon.data.entity.donor.BloodDonationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodDonationHistoryRepository extends JpaRepository<BloodDonationHistory, Long> {


    @Query("""
        SELECT 
            COUNT(bg) as totalDonation,
            COALESCE(SUM(bg.quantity), 0) as totalUnit,
            d.bloodType as bloodType
        FROM BloodDonationHistory bg
        JOIN bg.donor d
        WHERE d.user.id = :userId
        GROUP BY d.bloodType
    """)
    BloodDonationHistoryMetaData getBloodDonationHistoryMetaData(@Param("userId") Long userId);

    @Query("""
                SELECT bg
                FROM BloodDonationHistory bg
                WHERE bg.donor.user.id = :userId
                ORDER BY bg.lastDonation DESC
            """)
    List<BloodDonationHistory> findAllDonationHistoryByUserId(@Param("userId") Long userId);

}
