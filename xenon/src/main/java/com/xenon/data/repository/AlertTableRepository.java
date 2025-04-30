package com.xenon.data.repository;

import com.xenon.data.entity.alert.AlertSeverity;
import com.xenon.data.entity.alert.AlertTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface AlertTableRepository extends JpaRepository<AlertTable, Long> {

    // Find all active alerts created by a specific health authorization
    List<AlertTable> findByHealthAuthorization_IdAndIsActiveTrue(Long healthAuthorizationId);

    /*// Find all active alerts by severity level
    List<AlertTable> findByIsActiveTrueAndSeverityLevel(AlertSeverity severityLevel);

    // Find all active alerts within a date range
    List<AlertTable> findByIsActiveTrueAndStartDateBeforeAndEndDateAfterOrEndDateIsNull(
            ZonedDateTime currentDate, ZonedDateTime currentDate2);*/

    // Find all active alerts within a geographical area
    @Query(value = """
            SELECT a FROM AlertTable a 
            WHERE a.isActive = true 
            AND a.startDate <= :currentDate 
            AND (a.endDate >= :currentDate OR a.endDate IS NULL)
            AND (6371 * acos(cos(radians(:latitude)) * cos(radians(a.latitude)) 
                 * cos(radians(a.longitude) - radians(:longitude)) 
                 + sin(radians(:latitude)) * sin(radians(a.latitude)))) <= a.radius
            """)
    List<AlertTable> findActiveAlertsInRadius(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("currentDate") ZonedDateTime currentDate);

    // Find all active alerts by health authorization and within radius
    @Query(value = """
            SELECT a FROM AlertTable a 
            WHERE a.healthAuthorization.id = :healthAuthId 
            AND a.isActive = true 
            AND (6371 * acos(cos(radians(:latitude)) * cos(radians(a.latitude)) 
                 * cos(radians(a.longitude) - radians(:longitude)) 
                 + sin(radians(:latitude)) * sin(radians(a.latitude)))) <= a.radius
            """)
    List<AlertTable> findActiveAlertsByHealthAuthorizationAndWithinRadius(
            @Param("healthAuthId") Long healthAuthorizationId,
            @Param("latitude") double latitude,
            @Param("longitude") double longitude);
}