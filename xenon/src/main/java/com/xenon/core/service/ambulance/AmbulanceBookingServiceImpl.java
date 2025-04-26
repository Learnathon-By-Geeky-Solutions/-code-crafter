package com.xenon.core.service.ambulance;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.exception.UnauthorizedException;
import com.xenon.core.domain.request.ambulance.AmbulanceBookingRequest;
import com.xenon.core.domain.request.ambulance.UpdateBookingStatusRequest;
import com.xenon.core.domain.response.PageResponseRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.ambulance.Ambulance;
import com.xenon.data.entity.ambulance.AmbulanceBooking;
import com.xenon.data.entity.ambulance.AmbulanceBookingStatus;
import com.xenon.data.entity.ambulance.AmbulanceStatus;
import com.xenon.data.entity.user.User;
import com.xenon.data.entity.user.UserRole;
import com.xenon.data.repository.AmbulanceBookingRepository;
import com.xenon.data.repository.AmbulanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmbulanceBookingServiceImpl extends BaseService implements AmbulanceBookingService {

    private final AmbulanceRepository ambulanceRepository;
    private final AmbulanceBookingRepository ambulanceBookingRepository;

    @Override
    public ResponseEntity<?> createBooking(AmbulanceBookingRequest body) {
        validateBookingRequest(body);

        Ambulance ambulance = ambulanceRepository.findById(body.getAmbulanceId())
                .orElseThrow(() -> new ClientException("Ambulance not found"));

        if (ambulance.getAmbulanceStatus() == AmbulanceStatus.UNAVAILABLE) {
            throw new ClientException("This ambulance is currently unavailable");
        }

        try {
            AmbulanceBooking booking = ambulanceBookingRepository.save(body.toEntity(getCurrentUser(), ambulance));
            return success("Ambulance booking created successfully", booking.toResponse());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateBookingStatus(UpdateBookingStatusRequest body) {
        User currentUser = getCurrentUser();
        AmbulanceBooking booking = ambulanceBookingRepository.findById(body.getBookingId())
                .orElseThrow(() -> new ClientException("Booking not found"));

        // Only ambulance owner or admin can update booking status
        if (!isAmbulanceOwnerOrAdmin(currentUser, booking.getAmbulance())) {
            throw new UnauthorizedException("You are not authorized to update this booking's status");
        }

        try {
            booking.setStatus(body.getStatus());

            // If booking is completed or cancelled, set ambulance status back to available
            if (body.getStatus() == AmbulanceBookingStatus.COMPLETED ||
                    body.getStatus() == AmbulanceBookingStatus.CANCELLED) {

                Ambulance ambulance = booking.getAmbulance();
                ambulance.setAmbulanceStatus(AmbulanceStatus.AVAILABLE);
                ambulanceRepository.save(ambulance);
            } else if (body.getStatus() == AmbulanceBookingStatus.CONFIRMED) {
                // If booking is confirmed, set ambulance status to unavailable
                Ambulance ambulance = booking.getAmbulance();
                ambulance.setAmbulanceStatus(AmbulanceStatus.UNAVAILABLE);
                ambulanceRepository.save(ambulance);
            }

            AmbulanceBooking updatedBooking = ambulanceBookingRepository.save(booking);
            return success("Booking status updated successfully", updatedBooking.toResponse());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getUserBookings(Pageable pageable) {
        try {
            Page<AmbulanceBooking> bookingsPage = ambulanceBookingRepository.findByUser_Id(
                    getCurrentUser().getId(), pageable);

            List<Object> bookingResponses = bookingsPage.getContent().stream()
                    .map(AmbulanceBooking::toResponse)
                    .collect(Collectors.toList());

            PageResponseRequest<Object> pageResponse = new PageResponseRequest<>(
                    bookingResponses,
                    bookingsPage.getNumber(),
                    bookingsPage.getSize(),
                    bookingsPage.getTotalElements(),
                    bookingsPage.getTotalPages()
            );

            return success("User bookings retrieved successfully", pageResponse);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAmbulanceBookings(Long ambulanceId, Pageable pageable) {
        User currentUser = getCurrentUser();
        Ambulance ambulance = ambulanceRepository.findById(ambulanceId)
                .orElseThrow(() -> new ClientException("Ambulance not found"));

        // Only ambulance owner or admin can view all bookings
        if (!isAmbulanceOwnerOrAdmin(currentUser, ambulance)) {
            throw new UnauthorizedException("You are not authorized to view this ambulance's bookings");
        }

        try {
            Page<AmbulanceBooking> bookingsPage = ambulanceBookingRepository.findByAmbulance_Id(
                    ambulanceId, pageable);

            List<Object> bookingResponses = bookingsPage.getContent().stream()
                    .map(AmbulanceBooking::toResponse)
                    .collect(Collectors.toList());

            PageResponseRequest<Object> pageResponse = new PageResponseRequest<>(
                    bookingResponses,
                    bookingsPage.getNumber(),
                    bookingsPage.getSize(),
                    bookingsPage.getTotalElements(),
                    bookingsPage.getTotalPages()
            );

            return success("Ambulance bookings retrieved successfully", pageResponse);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getBookingDetails(Long bookingId) {
        User currentUser = getCurrentUser();
        AmbulanceBooking booking = ambulanceBookingRepository.findById(bookingId)
                .orElseThrow(() -> new ClientException("Booking not found"));

        // Only booking user, ambulance owner, or admin can view booking details
        if (!isBookingUserOrAmbulanceOwnerOrAdmin(currentUser, booking)) {
            throw new UnauthorizedException("You are not authorized to view this booking");
        }

        try {
            return success("Booking details retrieved successfully", booking.toResponse());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> cancelBooking(Long bookingId) {
        User currentUser = getCurrentUser();
        AmbulanceBooking booking = ambulanceBookingRepository.findById(bookingId)
                .orElseThrow(() -> new ClientException("Booking not found"));

        // Only booking user, ambulance owner, or admin can cancel booking
        if (!isBookingUserOrAmbulanceOwnerOrAdmin(currentUser, booking)) {
            throw new UnauthorizedException("You are not authorized to cancel this booking");
        }

        if (booking.getStatus() == AmbulanceBookingStatus.COMPLETED) {
            throw new ClientException("Cannot cancel a completed booking");
        }

        try {
            booking.setStatus(AmbulanceBookingStatus.CANCELLED);

            // Set ambulance status back to available
            Ambulance ambulance = booking.getAmbulance();
            ambulance.setAmbulanceStatus(AmbulanceStatus.AVAILABLE);
            ambulanceRepository.save(ambulance);

            AmbulanceBooking cancelledBooking = ambulanceBookingRepository.save(booking);
            return success("Booking cancelled successfully", cancelledBooking.toResponse());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    // Helper methods
    private void validateBookingRequest(AmbulanceBookingRequest body) {
        super.validateBody(body);

        if (body.getAmbulanceId() == null) throw requiredField("Ambulance ID");
    }

    private boolean isAmbulanceOwnerOrAdmin(User user, Ambulance ambulance) {
        return user.getRole() == UserRole.ADMIN ||
                (user.getRole() == UserRole.AMBULANCE && Objects.equals(user.getId(), ambulance.getUser().getId()));
    }

    private boolean isBookingUserOrAmbulanceOwnerOrAdmin(User user, AmbulanceBooking booking) {
        return user.getRole() == UserRole.ADMIN ||
                Objects.equals(user.getId(), booking.getUser().getId()) ||
                (user.getRole() == UserRole.AMBULANCE && Objects.equals(user.getId(), booking.getAmbulance().getUser().getId()));
    }
}