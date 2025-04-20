package com.xenon.core.service.pharmacy;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.request.pharmacy.CreatePharmacyAccountRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class PharmacyServiceImpl extends BaseService implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    @Override
    public ResponseEntity<?> createPharmacyRequest(CreatePharmacyAccountRequest body) {

        validateCreatePharmacyRequest(body);

        try {
            pharmacyRepository.save(body.toEntity(getCurrentUser()));
            return success("Pharmacy created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void validateCreatePharmacyRequest(CreatePharmacyAccountRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getTradeLicenseNumber())) throw requiredField("Trade license");
        if (isNullOrBlank(body.getDrugLicenseNumber())) throw requiredField("Drug license");
        if (isNullOrBlank(body.getOwnerNID())) throw requiredField("Owner NID");

        if (pharmacyRepository.existsByTradeLicenseNumber(body.getTradeLicenseNumber()))
            throw clientException("Registration number already exists!");
        if (pharmacyRepository.existsByDrugLicenseNumber(body.getDrugLicenseNumber()))
            throw clientException("Drug license number already exists!");
        if (pharmacyRepository.existsByOwnerNID(body.getOwnerNID())) throw clientException("Owner NID already exists!");
    }
}
