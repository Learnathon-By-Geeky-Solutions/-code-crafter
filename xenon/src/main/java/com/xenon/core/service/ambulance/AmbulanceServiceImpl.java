package com.xenon.core.service.ambulance;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.AuthException;
import com.xenon.core.domain.model.ResponseMessage;
import com.xenon.core.domain.request.ambulance.AmbulanceReviewRequest;
import com.xenon.core.domain.request.ambulance.CreateAmbulanceAccountRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.ambulance.Ambulance;
import com.xenon.data.entity.user.User;
import com.xenon.data.repository.AmbulanceRepository;
import com.xenon.data.repository.AmbulanceReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmbulanceServiceImpl extends BaseService implements AmbulanceService {

    private final AmbulanceRepository ambulanceRepository;
    private final AmbulanceReviewRepository ambulanceReviewRepository;

    @Override
    public ResponseEntity<?> createAmbulanceRequest(CreateAmbulanceAccountRequest body) {
        validateCreateAmbulanceRequest(body);

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
        Ambulance ambulance = ambulanceRepository.findById(body.getAmbulanceId()).orElseThrow(() -> new AuthException("Ambulance not found"));

        try {
            ambulanceReviewRepository.save(body.toEntity(getCurrentUser(), ambulance));
            return success("Ambulance review created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
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
        if (!isValidNumber(body.getResponse_time().toString())) throw clientException("Use only number for response time");
        if (!isValidNumber(body.getDoctors().toString())) throw clientException("Use only number for doctors");
        if (!isValidNumber(body.getNurses().toString())) throw clientException("Use only number for nurses");
        if (!isValidNumber(body.getParamedics().toString())) throw clientException("Use only number for paramedics");
        if (isNullOrBlank(body.getTeam_qualification())) throw requiredField("Team qualification");
        if (!isValidNumber(body.getStarting_fee().toString())) throw clientException("Use only number for starting fee");


        if (ambulanceRepository.existsByAmbulanceNumber(body.getAmbulanceNumber()))
            throw clientException("Ambulance number already exists!");
    }

    private void validateCreateAmbulanceReviewRequest(AmbulanceReviewRequest body) {
        super.validateBody(body);

        if (body.getRating() == null) throw requiredField("Rating");
        if (isNullOrBlank(body.getReview())) throw requiredField("Review");
    }

}
