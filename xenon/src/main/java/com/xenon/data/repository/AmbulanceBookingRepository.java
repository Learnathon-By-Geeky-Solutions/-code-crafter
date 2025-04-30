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

    Page<AmbulanceBooking> findByUser_Id(Long userId, Pageable pageable);

    Page<AmbulanceBooking> findByAmbulance_Id(Long ambulanceId, Pageable pageable);

    List<AmbulanceBooking> findByUser_IdAndAmbulance_IdAndStatus(Long userId, Long ambulanceId, AmbulanceBookingStatus status);

}