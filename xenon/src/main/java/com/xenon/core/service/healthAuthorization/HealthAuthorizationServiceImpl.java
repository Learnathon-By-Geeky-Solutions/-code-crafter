package com.xenon.core.service.healthAuthorization;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.exception.UnauthorizedException;
import com.xenon.core.domain.request.healthAuthorization.CreateAlertRequest;
import com.xenon.core.domain.request.healthAuthorization.CreateHealthAuthorizationAccountRequest;
import com.xenon.core.domain.request.healthAuthorization.UpdateAlertRequest;
import com.xenon.core.domain.response.alert.AlertResponse;
import com.xenon.core.service.common.BaseService;
import com.xenon.data.entity.alert.AlertTable;
import com.xenon.data.entity.healthAuthorization.HealthAuthorization;
import com.xenon.data.entity.user.UserRole;
import com.xenon.data.repository.AlertTableRepository;
import com.xenon.data.repository.HealthAuthorizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class HealthAuthorizationServiceImpl extends BaseService implements HealthAuthorizationService {

    private final HealthAuthorizationRepository healthAuthorizationRepository;
    private final AlertTableRepository alertTableRepository;

    @Override
    public ResponseEntity<?> createHealthAuthorizationRequest(CreateHealthAuthorizationAccountRequest body) {
        validateCreateHealthAuthorizationRequest(body);

        try {
            healthAuthorizationRepository.save(body.toEntity(getCurrentUser()));
            return success("Health Authorization created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> createAlertRequest(CreateAlertRequest body) {
        validateCreateAlertRequest(body);
        ensureUserIsHealthAuthorization();

        HealthAuthorization healthAuthorization = healthAuthorizationRepository
                .findByUserId(getCurrentUser().getId())
                .orElseThrow(() -> new ClientException("Health Authorization not found"));

        try {
            AlertTable alertTable = body.toEntity(healthAuthorization);
            alertTable = alertTableRepository.save(alertTable);

            return success("Alert created successfully", AlertResponse.fromEntity(alertTable));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateAlertRequest(Long alertId, UpdateAlertRequest body) {
        ensureUserIsHealthAuthorization();

        AlertTable alert = alertTableRepository.findById(alertId)
                .orElseThrow(() -> new ClientException("Alert not found"));

        // Verify ownership
        if (!alert.getHealthAuthorization().getUser().getId().equals(getCurrentUser().getId())) {
            throw new UnauthorizedException("You don't have permission to update this alert");
        }

        try {
            // Update fields if provided
            if (body.getTitle() != null) {
                alert.setTitle(body.getTitle());
            }

            if (body.getDescription() != null) {
                alert.setDescription(body.getDescription());
            }

            if (body.getAlertness() != null) {
                alert.setAlertness(body.getAlertness());
            }

            if (body.getLatitude() != null) {
                alert.setLatitude(body.getLatitude());
            }

            if (body.getLongitude() != null) {
                alert.setLongitude(body.getLongitude());
            }

            if (body.getRadius() != null) {
                alert.setRadius(body.getRadius());
            }

            if (body.getSeverityLevel() != null) {
                alert.setSeverityLevel(body.getSeverityLevel());
            }

            if (body.getIsActive() != null) {
                alert.setActive(body.getIsActive());
            }

            if (body.getStartDate() != null) {
                alert.setStartDate(body.getStartDate());
            }

            if (body.getEndDate() != null) {
                alert.setEndDate(body.getEndDate());
            }

            alert = alertTableRepository.save(alert);

            return success("Alert updated successfully", AlertResponse.fromEntity(alert));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deactivateAlert(Long alertId) {
        ensureUserIsHealthAuthorization();

        AlertTable alert = alertTableRepository.findById(alertId)
                .orElseThrow(() -> new ClientException("Alert not found"));

        // Verify ownership
        if (!alert.getHealthAuthorization().getUser().getId().equals(getCurrentUser().getId())) {
            throw new UnauthorizedException("You don't have permission to deactivate this alert");
        }

        try {
            alert.setActive(false);
            alert.setEndDate(ZonedDateTime.now());
            alertTableRepository.save(alert);

            return success("Alert deactivated successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteAlert(Long alertId) {
        ensureUserIsHealthAuthorization();

        AlertTable alert = alertTableRepository.findById(alertId)
                .orElseThrow(() -> new ClientException("Alert not found"));

        // Verify ownership
        if (!alert.getHealthAuthorization().getUser().getId().equals(getCurrentUser().getId())) {
            throw new UnauthorizedException("You don't have permission to delete this alert");
        }

        try {
            alertTableRepository.delete(alert);
            return success("Alert deleted successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAllAlerts() {


        HealthAuthorization healthAuthorization = healthAuthorizationRepository
                .findByUserId(getCurrentUser().getId())
                .orElseThrow(() -> new ClientException("Health Authorization not found"));
        try {
            List<AlertTable> alerts = alertTableRepository
                    .findByHealthAuthorization_IdAndIsActiveTrue(healthAuthorization.getId());

            List<AlertResponse> alertResponses = alerts.stream()
                    .map(AlertResponse::fromEntity)
                    .collect(Collectors.toList());

            return success("Alerts retrieved successfully", alertResponses);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAlertById(Long alertId) {
        ensureUserIsHealthAuthorization();

            AlertTable alert = alertTableRepository.findById(alertId)
                    .orElseThrow(() -> new ClientException("Alert not found"));

            // Verify ownership
            if (!alert.getHealthAuthorization().getUser().getId().equals(getCurrentUser().getId())) {
                throw new UnauthorizedException("You don't have permission to view this alert");
            }

            return success("Alert retrieved successfully", AlertResponse.fromEntity(alert));
    }

    private void validateCreateHealthAuthorizationRequest(CreateHealthAuthorizationAccountRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getAuthorizationNumber())) {
            throw requiredField("Authorization number");
        }

        if (healthAuthorizationRepository.existsByAuthorizationNumber(body.getAuthorizationNumber())) {
            throw clientException("Authorization number already exists!");
        }
    }

    private void validateCreateAlertRequest(CreateAlertRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getTitle())) {
            throw requiredField("Title");
        }

        if (isNullOrBlank(body.getDescription())) {
            throw requiredField("Description");
        }

        if (isNullOrBlank(body.getAlertness())) {
            throw requiredField("Alertness");
        }

        // Validate coordinates
        if (body.getLatitude() < -90 || body.getLatitude() > 90) {
            throw clientException("Latitude must be between -90 and 90 degrees");
        }

        if (body.getLongitude() < -180 || body.getLongitude() > 180) {
            throw clientException("Longitude must be between -180 and 180 degrees");
        }

        // Validate radius (must be positive)
        if (body.getRadius() <= 0) {
            throw clientException("Radius must be greater than 0");
        }
    }

    private void ensureUserIsHealthAuthorization() {
        if (getCurrentUser().getRole() != UserRole.HEALTH_AUTHORIZATION
                && getCurrentUser().getRole() != UserRole.ADMIN) {
            throw new UnauthorizedException("Only health authorization or admin users can perform this action");
        }
    }
}