package com.xenon.data.repository;

import com.xenon.data.entity.alert.UserAlertNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAlertNotificationRepository extends JpaRepository<UserAlertNotification, Long> {
    
    List<UserAlertNotification> findByUser_IdOrderByCreatedAtDesc(Long userId);
    
    Page<UserAlertNotification> findByUser_IdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    List<UserAlertNotification> findByUser_IdAndIsReadFalseOrderByCreatedAtDesc(Long userId);
    
    long countByUser_IdAndIsReadFalse(Long userId);
    
    Optional<UserAlertNotification> findByUser_IdAndAlert_Id(Long userId, Long alertId);
    
    boolean existsByUser_IdAndAlert_Id(Long userId, Long alertId);
    
    @Query("SELECT n FROM UserAlertNotification n WHERE n.user.id = :userId AND n.alert.id = :alertId")
    Optional<UserAlertNotification> findExistingNotification(
            @Param("userId") Long userId, 
            @Param("alertId") Long alertId);
}