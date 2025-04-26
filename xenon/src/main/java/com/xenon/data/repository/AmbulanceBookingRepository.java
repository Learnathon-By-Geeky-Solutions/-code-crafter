package com.xenon.data.repository;

import com.xenon.data.entity.ambulance.AmbulanceBooking;
import com.xenon.data.entity.ambulance.AmbulanceBookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbulanceBookingRepository extends JpaRepository<AmbulanceBooking, Long> {
    List<AmbulanceBooking> findByUser_Id(Long userId);

    Page<AmbulanceBooking> findByUser_Id(Long userId, Pageable pageable);

    List<AmbulanceBooking> findByAmbulance_Id(Long ambulanceId);

    Page<AmbulanceBooking> findByAmbulance_Id(Long ambulanceId, Pageable pageable);

    List<AmbulanceBooking> findByAmbulance_IdAndStatus(Long ambulanceId, AmbulanceBookingStatus status);
    List<AmbulanceBooking> findByUser_IdAndAmbulance_IdAndStatus(Long userId, Long ambulanceId, AmbulanceBookingStatus status);

    Page<AmbulanceBooking> findByAmbulance_IdAndStatus(Long ambulanceId, AmbulanceBookingStatus status, Pageable pageable);

    List<AmbulanceBooking> findByUser_IdAndStatus(Long userId, AmbulanceBookingStatus status);

    Page<AmbulanceBooking> findByUser_IdAndStatus(Long userId, AmbulanceBookingStatus status, Pageable pageable);
}