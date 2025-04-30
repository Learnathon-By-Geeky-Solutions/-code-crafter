package com.xenon.core.service.pharmacy;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.pharmacy.CreatePharmacyAccountRequest;
import com.xenon.core.domain.response.pharmacy.PharmacyResponse;
import com.xenon.core.service.common.BaseService;
import com.xenon.data.entity.pharmacy.Pharmacy;
import com.xenon.data.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public ResponseEntity<?> getPharmacyById(Long id) {

            Pharmacy  pharmacy = pharmacyRepository.findById(id).orElseThrow(() -> new ClientException("Pharmacy not found with id: " + id));
        try {
            // Create response object
            PharmacyResponse response = mapToPharmacyResponse(pharmacy);

            return success("Pharmacy retrieved successfully", response);
        } catch (Exception e) {
            log.error("Error retrieving pharmacy: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAllPharmacies(Pageable pageable) {
        try {
            Page<Pharmacy> pharmacies = pharmacyRepository.findAll(pageable);

            // Map to response objects
            return getResponseEntity(pharmacies);
        } catch (Exception e) {
            log.error("Error retrieving all pharmacies: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private ResponseEntity<?> getResponseEntity(Page<Pharmacy> pharmacies) {
        List<PharmacyResponse> responseList = pharmacies.getContent().stream()
                .map(this::mapToPharmacyResponse)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("content", responseList);
        response.put("currentPage", pharmacies.getNumber());
        response.put("totalItems", pharmacies.getTotalElements());
        response.put("totalPages", pharmacies.getTotalPages());
        response.put("size", pharmacies.getSize());

        return success("Pharmacies retrieved successfully", response);
    }

    @Override
    public ResponseEntity<?> searchPharmacies(String name, Pageable pageable) {
        try {
            Page<Pharmacy> pharmacies = pharmacyRepository.findByUserName(name, pageable);

            // Map to response objects
            return getResponseEntity(pharmacies);
        } catch (Exception e) {
            log.error("Error searching pharmacies: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getPharmaciesByUpazilaId(Long upazilaId, Pageable pageable) {
        try {
            Page<Pharmacy> pharmacies = pharmacyRepository.findByUpazilaId(upazilaId, pageable);

            // Map to response objects
            List<PharmacyResponse> responseList = pharmacies.getContent().stream()
                    .map(this::mapToPharmacyResponse)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("content", responseList);
            response.put("currentPage", pharmacies.getNumber());
            response.put("totalItems", pharmacies.getTotalElements());
            response.put("totalPages", pharmacies.getTotalPages());
            response.put("size", pharmacies.getSize());

            return success("Pharmacies by upazila retrieved successfully", response);
        } catch (Exception e) {
            log.error("Error retrieving pharmacies by upazila: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    // Helper method to map Pharmacy entity to PharmacyResponse DTO
    private PharmacyResponse mapToPharmacyResponse(Pharmacy pharmacy) {
        PharmacyResponse response = new PharmacyResponse();
        response.setId(pharmacy.getId());
        response.setTradeLicenseNumber(pharmacy.getTradeLicenseNumber());

        if (pharmacy.getUser() != null) {
            // Concatenate first and last name for pharmacy name
            String firstName = pharmacy.getUser().getFirstName() != null ? pharmacy.getUser().getFirstName() : "";
            String lastName = pharmacy.getUser().getLastName() != null ? pharmacy.getUser().getLastName() : "";
            response.setPharmacyName(firstName + " " + lastName);

            response.setPhone(pharmacy.getUser().getPhone());
            response.setEmail(pharmacy.getUser().getEmail());
            response.setArea(pharmacy.getUser().getArea());
            response.setLatitude(pharmacy.getUser().getLatitude());
            response.setLongitude(pharmacy.getUser().getLongitude());

            // Set location information if available
            if (pharmacy.getUser().getUpazila() != null) {
                response.setUpazilaName(pharmacy.getUser().getUpazila().getName());

                if (pharmacy.getUser().getUpazila().getDistrict() != null) {
                    response.setDistrictName(pharmacy.getUser().getUpazila().getDistrict().getName());

                    if (pharmacy.getUser().getUpazila().getDistrict().getDivision() != null) {
                        response.setDivisionName(pharmacy.getUser().getUpazila().getDistrict().getDivision().getName());
                    }
                }
            }
        }

        return response;
    }

    private void validateCreatePharmacyRequest(CreatePharmacyAccountRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getTradeLicenseNumber())) throw requiredField("Trade license");

        if (pharmacyRepository.existsByTradeLicenseNumber(body.getTradeLicenseNumber()))
            throw clientException("Registration number already exists!");
    }
}