package com.xenon.core.service.ambulance;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.AuthException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.exception.UnauthorizedException;
import com.xenon.core.domain.request.ambulance.AmbulanceReviewRequest;
import com.xenon.core.domain.request.ambulance.CreateAmbulanceAccountRequest;
import com.xenon.core.domain.response.PageResponseRequest;
import com.xenon.core.domain.response.ambulance.AmbulanceDetailedResponse;
import com.xenon.core.domain.response.ambulance.AmbulanceListResponse;
import com.xenon.core.domain.response.ambulance.AmbulanceResponse;
import com.xenon.core.domain.response.ambulance.projection.AmbulanceMetadataProjection;
import com.xenon.core.service.common.BaseService;
import com.xenon.data.entity.ambulance.*;
import com.xenon.data.entity.user.UserRole;
import com.xenon.data.repository.AmbulanceBookingRepository;
import com.xenon.data.repository.AmbulanceRepository;
import com.xenon.data.repository.AmbulanceReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmbulanceServiceImpl extends BaseService implements AmbulanceService {

    private final AmbulanceRepository ambulanceRepository;
    private final AmbulanceReviewRepository ambulanceReviewRepository;
    private final AmbulanceBookingRepository ambulanceBookingRepository;

    @Override
    public ResponseEntity<?> createAmbulanceRequest(CreateAmbulanceAccountRequest body) {
        validateCreateAmbulanceRequest(body);
        userRepository.findById(getCurrentUser().getId()).orElseThrow(() -> new AuthException("User not found"));

        try {
            ambulanceRepository.save(body.toEntity(getCurrentUser()));
            return success("Ambulance created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> createAmbulanceReviewRequest(AmbulanceReviewRequest body) {
        validateCreateAmbulanceReviewRequest(body);

        // Check if the user can review this ambulance (must have completed booking)
        if (!canUserReviewAmbulance(getCurrentUser().getId(), body.getAmbulanceId())) {
            throw new ClientException("You can only review ambulances that you have used (must have a completed booking)");
        }

        Ambulance ambulance = ambulanceRepository.findById(body.getAmbulanceId())
                .orElseThrow(() -> new AuthException("Ambulance not found"));

        try {
            ambulanceReviewRepository.save(body.toEntity(getCurrentUser(), ambulance));
            return success("Ambulance review created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAmbulanceList(AmbulanceType type, Pageable pageable) {

            AmbulanceMetadataProjection metadata = ambulanceRepository.getAmbulanceMetadata(type.name());
            Page<Ambulance> ambulancesPage = ambulanceRepository.findAllByAmbulanceTypeAndAmbulanceStatus(
                    type, AmbulanceStatus.AVAILABLE, pageable);

            List<AmbulanceResponse> ambulanceResponses = ambulancesPage.getContent().stream()
                    .map(Ambulance::toResponse)
                    .collect(Collectors.toList());

            PageResponseRequest<AmbulanceResponse> pageResponse = new PageResponseRequest<>(
                    ambulanceResponses,
                    ambulancesPage.getNumber(),
                    ambulancesPage.getSize(),
                    ambulancesPage.getTotalElements(),
                    ambulancesPage.getTotalPages()
            );
        try {
            return success(
                    "Ambulance list retrieved successfully",
                    new AmbulanceListResponse(
                            metadata.getAmbulanceCount(),
                            metadata.getDoctorCount(),
                            pageResponse
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAmbulanceById(Long ambulanceId) {
            Ambulance ambulance = ambulanceRepository.findById(ambulanceId)
                    .orElseThrow(() -> new ClientException("Ambulance not found"));

            // Get rating information
            Double averageRating = ambulanceReviewRepository.getAverageRatingByAmbulanceId(ambulanceId);
            Integer totalReviews = ambulanceReviewRepository.getReviewCountByAmbulanceId(ambulanceId);

            try {
            AmbulanceDetailedResponse response = new AmbulanceDetailedResponse(
                    ambulance.getId(),
                    ambulance.getUser().toResponse(),
                    ambulance.getAmbulanceType(),
                    ambulance.getAmbulanceNumber(),
                    ambulance.getAmbulanceStatus(),
                    ambulance.getAbout(),
                    // Convert comma-separated strings to lists
                    ambulance.getService_offers() != null ? Arrays.asList(ambulance.getService_offers().split(",")) : null,
                    ambulance.getHospital_affiliation(),
                    ambulance.getCoverage_areas() != null ? Arrays.asList(ambulance.getCoverage_areas().split(",")) : null,
                    ambulance.getResponse_time(),
                    ambulance.getDoctors(),
                    ambulance.getNurses(),
                    ambulance.getParamedics(),
                    ambulance.getTeam_qualification() != null ? Arrays.asList(ambulance.getTeam_qualification().split(",")) : null,
                    ambulance.getStarting_fee(),
                    averageRating != null ? averageRating : 0.0,
                    totalReviews != null ? totalReviews : 0
            );

            return success("Ambulance details retrieved successfully", response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAmbulancesByArea(AmbulanceType type, String area, Pageable pageable) {

            // Get ambulances by area
            Page<Ambulance> ambulancesPage = ambulanceRepository.findByAmbulanceTypeAndAreaContaining(
                    type, area, pageable);

            // Get metadata for the ambulance type
            AmbulanceMetadataProjection metadata = ambulanceRepository.getAmbulanceMetadata(type.name());

            List<AmbulanceResponse> ambulanceResponses = ambulancesPage.getContent().stream()
                    .map(Ambulance::toResponse)
                    .collect(Collectors.toList());

            PageResponseRequest<AmbulanceResponse> pageResponse = new PageResponseRequest<>(
                    ambulanceResponses,
                    ambulancesPage.getNumber(),
                    ambulancesPage.getSize(),
                    ambulancesPage.getTotalElements(),
                    ambulancesPage.getTotalPages()
            );
        try {
            return success(
                    "Ambulances filtered by area retrieved successfully",
                    new AmbulanceListResponse(
                            (int) ambulancesPage.getTotalElements(),
                            metadata.getDoctorCount(),
                            pageResponse
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAmbulanceReviews(Long ambulanceId, Pageable pageable) {
            // Check if ambulance exists
            if (!ambulanceRepository.existsById(ambulanceId)) {
                throw new ClientException("Ambulance not found");
            }
        try {

            Page<AmbulanceReview> reviewsPage = ambulanceReviewRepository.findAllByAmbulance_Id(ambulanceId, pageable);

            List<Object> reviewResponses = reviewsPage.getContent().stream()
                    .map(AmbulanceReview::toResponse)
                    .collect(Collectors.toList());

            PageResponseRequest<Object> pageResponse = new PageResponseRequest<>(
                    reviewResponses,
                    reviewsPage.getNumber(),
                    reviewsPage.getSize(),
                    reviewsPage.getTotalElements(),
                    reviewsPage.getTotalPages()
            );

            return success("Ambulance reviews retrieved successfully", pageResponse);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateAmbulanceStatus(Long ambulanceId, AmbulanceStatus status) {

            Ambulance ambulance = ambulanceRepository.findById(ambulanceId)
                    .orElseThrow(() -> new ClientException("Ambulance not found"));

            // Check if the current user is the ambulance owner or an admin
            if (getCurrentUser().getRole() != UserRole.ADMIN &&
                    !Objects.equals(ambulance.getUser().getId(), getCurrentUser().getId())) {
                throw new UnauthorizedException("You are not authorized to update this ambulance status");
            }
        try {
            ambulance.setAmbulanceStatus(status);
            ambulanceRepository.save(ambulance);
            return success("Ambulance status updated successfully", ambulance.toResponse());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> checkUserCanReview(Long ambulanceId) {
        try {
            boolean canReview = canUserReviewAmbulance(getCurrentUser().getId(), ambulanceId);
            return success("User review eligibility checked", canReview);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    // Helper methods
    private boolean canUserReviewAmbulance(Long userId, Long ambulanceId) {
        // Check if user has any completed bookings with this ambulance
        return !ambulanceBookingRepository.findByUser_IdAndAmbulance_IdAndStatus(
                userId, ambulanceId, AmbulanceBookingStatus.CONFIRMED).isEmpty();
    }

    private void validateCreateAmbulanceRequest(CreateAmbulanceAccountRequest body) {
        super.validateBody(body);

        if (body.getAmbulanceType() == null) throw requiredField("Ambulance type");
        if (body.getAmbulanceNumber() == null) throw requiredField("Ambulance number");
        if (body.getAmbulanceStatus() == null) throw requiredField("Ambulance status");
        if (isNullOrBlank(body.getAbout())) throw requiredField("About");
        if (isNullOrBlank(body.getService_offers())) throw requiredField("Service offers");
        if (isNullOrBlank(body.getHospital_affiliation())) throw requiredField("Hospital affiliation");
        if (isNullOrBlank(body.getCoverage_areas())) throw requiredField("Coverage areas");
        if (body.getResponse_time() == null) throw requiredField("Response time");
        if (!isValidNumber(body.getResponse_time().toString()))
            throw clientException("Use only number for response time");
        if (!isValidNumber(body.getDoctors().toString())) throw clientException("Use only number for doctors");
        if (!isValidNumber(body.getNurses().toString())) throw clientException("Use only number for nurses");
        if (!isValidNumber(body.getParamedics().toString())) throw clientException("Use only number for paramedics");
        if (isNullOrBlank(body.getTeam_qualification())) throw requiredField("Team qualification");
        if (!isValidNumber(body.getStarting_fee().toString()))
            throw clientException("Use only number for starting fee");


        if (ambulanceRepository.existsByAmbulanceNumber(body.getAmbulanceNumber()))
            throw clientException("Ambulance number already exists!");
    }

    private void validateCreateAmbulanceReviewRequest(AmbulanceReviewRequest body) {
        super.validateBody(body);

        if (body.getRating() == null) throw requiredField("Rating");
        if (isNullOrBlank(body.getReview())) throw requiredField("Review");
    }
}