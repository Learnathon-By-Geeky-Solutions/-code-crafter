package com.xenon.data.repository;

import com.xenon.core.domain.response.ambulance.projection.AmbulanceMetadataProjection;
import com.xenon.data.entity.ambulance.Ambulance;
import com.xenon.data.entity.ambulance.AmbulanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbulanceRepository extends JpaRepository<Ambulance, Long> {
    boolean existsByAmbulanceNumber(String ambulanceNumber);

    @Query(
            value = """
                    SELECT COUNT(*)       AS ambulanceCount,
                           COUNT(doctors) as doctorCount
                    FROM ambulance a
                    WHERE a.ambulance_type = :type;
                    """,
            nativeQuery= true
    )
    AmbulanceMetadataProjection getAmbulanceMetadata(@Param("type") String ambulanceType);

    List<Ambulance> findAllByAmbulanceType(AmbulanceType ambulanceType);
}
