package com.xenon.core.service.healthAuthorization;

import com.xenon.core.domain.request.healthAuthorization.CreateAlertRequest;
import com.xenon.core.domain.request.healthAuthorization.CreateHealthAuthorizationAccountRequest;
import com.xenon.core.domain.request.healthAuthorization.UpdateAlertRequest;
import org.springframework.http.ResponseEntity;

public interface HealthAuthorizationService {
    /**
     * Creates a health authorization account
     *
     * @param body Request containing the authorization number
     * @return Response with the result of the operation
     */
    ResponseEntity<?> createHealthAuthorizationRequest(CreateHealthAuthorizationAccountRequest body);

    /**
     * Creates a new health alert
     *
     * @param body Request containing alert details
     * @return Response with the result of the operation
     */
    ResponseEntity<?> createAlertRequest(CreateAlertRequest body);

    /**
     * Updates an existing health alert
     *
     * @param alertId ID of the alert to update
     * @param body Request containing updated alert details
     * @return Response with the result of the operation
     */
    ResponseEntity<?> updateAlertRequest(Long alertId, UpdateAlertRequest body);

    /**
     * Deactivates an existing health alert (soft delete)
     *
     * @param alertId ID of the alert to deactivate
     * @return Response with the result of the operation
     */
    ResponseEntity<?> deactivateAlert(Long alertId);

    /**
     * Hard deletes an existing health alert
     *
     * @param alertId ID of the alert to delete
     * @return Response with the result of the operation
     */
    ResponseEntity<?> deleteAlert(Long alertId);

    /**
     * Gets all alerts created by the current health authorization
     *
     * @return Response with the list of alerts
     */
    ResponseEntity<?> getAllAlerts();

    /**
     * Gets a specific alert by ID
     *
     * @param alertId ID of the alert to retrieve
     * @return Response with the alert details
     */
    ResponseEntity<?> getAlertById(Long alertId);
}