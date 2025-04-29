package com.xenon.core.service.ambulance;

import com.xenon.core.domain.request.ambulance.AmbulanceBookingRequest;
import com.xenon.core.domain.request.ambulance.UpdateBookingStatusRequest;
import com.xenon.data.entity.ambulance.AmbulanceBookingStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AmbulanceBookingService {
    ResponseEntity<?> createBooking(AmbulanceBookingRequest body);
    ResponseEntity<?> updateBookingStatus(UpdateBookingStatusRequest body, AmbulanceBookingStatus status);
    ResponseEntity<?> getUserBookings(Pageable pageable);
    ResponseEntity<?> getAmbulanceBookings(Long ambulanceId, Pageable pageable);
    ResponseEntity<?> getBookingDetails(Long bookingId);
    ResponseEntity<?> cancelBooking(Long bookingId);
}