package com.xenon.core.service.blood;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.request.DonorRequest;
import com.xenon.core.service.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class BloodServiceImpl extends BaseService implements BloodService {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+?\\d{1,4})?[-\\s]?\\(?\\d{1,4}\\)?[-\\s]?\\d{1,4}[-\\s]?\\d{1,4}$");

    @Override
    public ResponseEntity<?> createDonorRequest(DonorRequest body) {
        validateCreateDonorRequest(body);

        try {
            // Donor donor = respository.save(body.toEntity());
            return success("Donor created successfully", null);
        } catch (Exception e) {
            throw new ApiException(e);
        }
    }

    private void validateCreateDonorRequest(DonorRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getName())) throw requiredField("name");
        if (isNullOrBlank(body.getPhone())) throw requiredField("phone");
        if (!PHONE_PATTERN.matcher(body.getPhone()).matches()) throw clientException("Invalid phone number");
    }
}
