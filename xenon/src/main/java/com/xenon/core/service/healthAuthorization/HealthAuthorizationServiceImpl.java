package com.xenon.core.service.healthAuthorization;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.healthAuthorization.CreateAlertRequest;
import com.xenon.core.domain.request.healthAuthorization.CreateHealthAuthorizationAccountRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.healthAuthorization.HealthAuthorization;
import com.xenon.data.entity.location.Upazila;
import com.xenon.data.repository.AlertTableRepository;
import com.xenon.data.repository.HealthAuthorizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class HealthAuthorizationServiceImpl extends BaseService implements HealthAuthorizationService {

    private final HealthAuthorizationRepository healthAuthorizationRepository;
    private final AlertTableRepository alterTableRepository;

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
    public ResponseEntity<?> createAlertRequest(CreateAlertRequest body) {
        validateCreateAlertRequest(body);

        HealthAuthorization healthAuthorization = healthAuthorizationRepository.findByUserId(getCurrentUser().getId()).orElseThrow(() -> new ClientException("Health Authorization not found"));//.orElseThrow(() -> new ClientException("Health Authorization not found"));
        try {
            alterTableRepository.save(body.toEntity(healthAuthorization));
            return success("Alert created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }

    }





    private void validateCreateHealthAuthorizationRequest(CreateHealthAuthorizationAccountRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getAuthorizationNumber())) throw requiredField("Registration number");
        if (healthAuthorizationRepository.existsByAuthorizationNumber(body.getAuthorizationNumber()))
            throw clientException("Registration number already exists!");

    }

    private void validateCreateAlertRequest(CreateAlertRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getTitle())) throw requiredField("Title");
        if (isNullOrBlank(body.getDescription())) throw requiredField("Description");
        if (isNullOrBlank(body.getAlertness())) throw requiredField("Alertness");
    }

}

