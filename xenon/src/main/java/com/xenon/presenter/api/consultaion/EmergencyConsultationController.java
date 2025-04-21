package com.xenon.presenter.api.consultaion;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.consultation.CreateEmergencyConsultationRequest;
import com.xenon.core.domain.request.consultation.CreateEmergencyConsultationTableRequest;
import com.xenon.core.service.consultation.EmergencyConsultationService;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/emergency-consultation")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class EmergencyConsultationController {

    private final EmergencyConsultationService emergencyConsultationService;

    @PostMapping("create-emergency-consultation")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createEmergencyConsultationRequest(@Nullable @RequestBody CreateEmergencyConsultationRequest body) {
        return emergencyConsultationService.createEmergencyConsultationRequest(body);
    }

    @PostMapping("create-emergency-consultation-table")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createEmergencyConsultationTableRequest(@Nullable @RequestBody CreateEmergencyConsultationTableRequest body) {
        return emergencyConsultationService.createEmergencyConsultationTableRequest(body);

    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize(shouldCheckAccountStatus = true)
    public ResponseEntity<?> getSpecialistDoctors() {
        return emergencyConsultationService.getSpecialistDoctors();
    }
}
