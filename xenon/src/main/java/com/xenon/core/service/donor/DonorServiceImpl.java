package com.xenon.core.service.donor;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.donor.BloodDonationInfoRequest;
import com.xenon.core.domain.request.donor.CreateDonorAccountRequest;
import com.xenon.core.domain.request.donor.UpdateDonorInterestRequest;
import com.xenon.core.domain.response.PageResponseRequest;
import com.xenon.core.domain.response.donor.BloodDonationHistoryListResponse;
import com.xenon.core.domain.response.donor.DonorProfileResponse;
import com.xenon.core.domain.response.donor.DonorResponse;
import com.xenon.core.domain.response.donor.projection.BloodDonationHistoryMetaData;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.donor.BloodDonationHistory;
import com.xenon.data.entity.donor.BloodType;
import com.xenon.data.entity.donor.Donor;
import com.xenon.data.repository.BloodDonationHistoryRepository;
import com.xenon.data.repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonorServiceImpl extends BaseService implements DonorService {

    private final DonorRepository donorRepository;
    private final BloodDonationHistoryRepository bloodDonationHistoryRepository;


    @Override
    public ResponseEntity<?> createDonorAccountRequest(CreateDonorAccountRequest body) {

        validateCreateDonorAccountRequest(body);

        try {
            donorRepository.save(body.toEntity(getCurrentUser()));
            return success("Donor created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> bloodGivenInfoRequest(BloodDonationInfoRequest body) {
        validateBloodGivenInfoRequest(body);

        Donor donor = donorRepository.findByUserId(getCurrentUser().getId()).orElseThrow(() -> new ClientException("Donor not found"));

        try {

            bloodDonationHistoryRepository.save(body.toEntity(donor));
            return success("Success", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getDonationHistory() {
        BloodDonationHistoryMetaData metaData = bloodDonationHistoryRepository.getBloodDonationHistoryMetaData(getCurrentUser().getId());

        if (metaData == null) {
            return success("Donation History Fetched Successfully", new BloodDonationHistoryListResponse(0L, 0L, null, null));
        }

        List<BloodDonationHistory> bloodDonationHistories = bloodDonationHistoryRepository.findAllDonationHistoryByUserId(getCurrentUser().getId());

        return success("Donation History Fetched Successfully",
                new BloodDonationHistoryListResponse(
                        metaData.getTotalDonation(),
                        metaData.getTotalUnit(),
                        metaData.getBloodType(),
                        bloodDonationHistories.stream().map(BloodDonationHistory::toResponse).toList()
                ));
    }

    @Override
    public ResponseEntity<?> updateDonorInterest(UpdateDonorInterestRequest body) {
        validateUpdateDonorInterestRequest(body);

        try {
            Donor donor = donorRepository.findByUserId(getCurrentUser().getId())
                    .orElseThrow(() -> new ClientException("Donor not found"));

            donor.setInterested(body.getInterested());
            donorRepository.save(donor);

            return success("Donor interest updated successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getDonorProfile() {
        try {
            Donor donor = donorRepository.findByUserId(getCurrentUser().getId())
                    .orElseThrow(() -> new ClientException("Donor not found"));

            DonorProfileResponse response = new DonorProfileResponse(
                    donor.getId(),
                    donor.getUser().toResponse(),
                    donor.getBloodType(),
                    donor.getAge(),
                    donor.getWeight(),
                    donor.getInterested(),
                    donor.getLastDonation(),
                    donor.isAvailableForDonation()
            );

            return success("Donor profile retrieved successfully", response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAvailableDonors(BloodType bloodType, Long upazilaId, Pageable pageable) {
        try {
            Page<Donor> donorsPage;

            if (bloodType != null && upazilaId != null) {
                donorsPage = donorRepository.findByBloodTypeAndUpazilaAndInterestedPage(
                        bloodType, upazilaId, pageable);
            } else if (bloodType != null) {
                donorsPage = donorRepository.findByBloodTypeAndInterestedPage(bloodType, pageable);
            } else if (upazilaId != null) {
                donorsPage = donorRepository.findByUpazilaAndInterestedPage(upazilaId, pageable);
            } else {
                donorsPage = donorRepository.findByInterestedPage(pageable);
            }

            List<DonorResponse> donorResponses = donorsPage.getContent().stream()
                    .filter(Donor::isAvailableForDonation)
                    .map(Donor::toResponse)
                    .collect(Collectors.toList());

            PageResponseRequest<DonorResponse> response = new PageResponseRequest<>(
                    donorResponses,
                    donorsPage.getNumber(),
                    donorsPage.getSize(),
                    donorsPage.getTotalElements(),
                    donorsPage.getTotalPages()
            );

            return success("Available donors retrieved successfully", response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void validateUpdateDonorInterestRequest(UpdateDonorInterestRequest body) {
        super.validateBody(body);
        if (body.getInterested() == null) throw requiredField("Interested");
    }

    private void validateCreateDonorAccountRequest(CreateDonorAccountRequest body) {
        super.validateBody(body);

        if (body.getBloodType() == null) throw requiredField("Blood type");
        if (body.getInterested() == null) throw requiredField("Interested Choice");
        if (body.getAge() == null) throw requiredField("Age");
        if (body.getWeight() == null) throw requiredField("Weight");
        if (!isValidNumber(body.getAge().toString())) throw clientException("Use only number for age");
        if (!isValidNumber(body.getWeight().toString())) throw clientException("Use only number for weight");
        if (donorRepository.existsByUserId(getCurrentUser().getId())) throw clientException("Donor already exists!");
    }

    private void validateBloodGivenInfoRequest(BloodDonationInfoRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getPatientName())) throw requiredField("Patient Name");
        if (isNullOrBlank(body.getHospitalName())) throw requiredField("Hospital Name");
        if (body.getQuantity() == null) throw requiredField("Quantity");
        if (!isValidNumber(body.getQuantity().toString())) throw clientException("Use only number for quantity");
        if (body.getLastDonation() == null) throw requiredField("Last Donation");
    }
}
