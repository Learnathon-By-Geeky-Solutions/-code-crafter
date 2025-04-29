package com.xenon.core.service.bloodBank;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.bloodBank.CreateBloodBankAccountRequest;
import com.xenon.core.domain.response.PageResponseRequest;
import com.xenon.core.domain.response.bloodBank.BloodBankResponse;
import com.xenon.core.service.common.BaseService;
import com.xenon.data.entity.bloodBank.BloodBank;
import com.xenon.data.repository.BloodBankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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

    @Override
    public ResponseEntity<?> getAllBloodBanks(Pageable pageable) {
        try {
            Page<BloodBank> bloodBanksPage = bloodBankRepository.findAll(pageable);

            PageResponseRequest<BloodBankResponse> pageResponse = createBloodBanksPageResponse(bloodBanksPage);

            return success("Blood banks retrieved successfully", pageResponse);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getBloodBanksByLocation(Long upazilaId, Pageable pageable) {
        try {
            Page<BloodBank> bloodBanksPage = bloodBankRepository.findByUpazilaId(upazilaId, pageable);

            PageResponseRequest<BloodBankResponse> pageResponse = createBloodBanksPageResponse(bloodBanksPage);

            return success("Blood banks by location retrieved successfully", pageResponse);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getBloodBankDetails(Long bloodBankId) {
            BloodBank bloodBank = bloodBankRepository.findById(bloodBankId)
                    .orElseThrow(() -> new ClientException("Blood bank not found"));
        try {
            BloodBankResponse response = convertToBloodBankResponse(bloodBank);

            return success("Blood bank details retrieved successfully", response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private PageResponseRequest<BloodBankResponse> createBloodBanksPageResponse(Page<BloodBank> bloodBanksPage) {
        List<BloodBankResponse> responses = bloodBanksPage.getContent().stream()
                .map(this::convertToBloodBankResponse)
                .collect(Collectors.toList());

        return new PageResponseRequest<>(
                responses,
                bloodBanksPage.getNumber(),
                bloodBanksPage.getSize(),
                bloodBanksPage.getTotalElements(),
                bloodBanksPage.getTotalPages()
        );
    }

    private BloodBankResponse convertToBloodBankResponse(BloodBank bloodBank) {
        return new BloodBankResponse(
                bloodBank.getId(),
                bloodBank.getRegistrationNumber(),
                bloodBank.getUser().toResponse(),
                bloodBank.getUser().getLatitude(),
                bloodBank.getUser().getLongitude()
        );
    }

    private void validateBloodBankRequest(CreateBloodBankAccountRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getRegistration_number())) throw requiredField("Registration number is required");
        if (bloodBankRepository.existsByRegistrationNumber(body.getRegistration_number()))
            throw clientException("Registration number already exists!");
    }
}
