package com.xenon.core.service.bloodBank;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.request.bloodBank.CreateBloodBankAccountRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.repository.BloodBankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Slf4j
@Service
public class BloodBankServiceImpl extends BaseService implements BloodBankService {

    private final BloodBankRepository bloodBankRepository;

    @Override
    public ResponseEntity<?> createBloodBankRequest(CreateBloodBankAccountRequest body) {
        validateBloodBankRequest(body);

        try {
            bloodBankRepository.save(body.toEntity(getCurrentUser()));
            return success("Blood Bank created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void validateBloodBankRequest(CreateBloodBankAccountRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getRegistration_number())) throw requiredField("Registration number is required");
        if (bloodBankRepository.existsByRegistrationNumber(body.getRegistration_number()))
            throw clientException("Registration number already exists!");
    }
}
