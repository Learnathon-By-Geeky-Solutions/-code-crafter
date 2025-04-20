package com.xenon.core.service.blood;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.blood.CreateBloodRequestPost;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.location.Upazila;
import com.xenon.data.repository.BloodRequestPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BloodRequestPostServiceImpl extends BaseService implements BloodRequestPostService {

    private final BloodRequestPostRepository bloodRequestRepositoryPost;
    @Override
    public ResponseEntity<?> createBloodRequestPost(CreateBloodRequestPost body) {

        validateCreateBloodRequestPost(body);
        Upazila upazila = upazilaRepository.findById(body.getUpazilaId()).orElseThrow(() -> new ClientException("No upazila Found"));

        try {
            bloodRequestRepositoryPost.save(body.toEntity(getCurrentUser(), upazila));
            return success("Request created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void validateCreateBloodRequestPost(CreateBloodRequestPost body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getPatientName())) throw requiredField("Patient Name");
        if (body.getBloodType() == null) throw requiredField("Blood Type");
        if (body.getQuantity() == null) throw requiredField("Quantity");
        if (isNullOrBlank(body.getHospitalName())) throw requiredField("Hospital Name");
        if (isNullOrBlank(body.getContactNumber())) throw requiredField("Contact Number");
        if (isNullOrBlank(body.getDescription())) throw requiredField("Description");

        if (!isValidNumber(body.getQuantity().toString())) throw clientException("Use only number for quantity");
        if (!PHONE_PATTERN.matcher(body.getContactNumber()).matches()) throw clientException("Invalid phone number");
        if (body.getDate() == null) throw requiredField("Date");
    }
}
