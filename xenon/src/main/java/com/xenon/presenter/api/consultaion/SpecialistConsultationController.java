package com.xenon.presenter.api.consultaion;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.consultation.CreateSpecialistConsultationAppointmentTableRequest;
import com.xenon.core.domain.request.consultation.CreateSpecialistConsultationRequest;
import com.xenon.core.service.consultation.SpecialistConsultationService;
import com.xenon.data.entity.user.UserRole;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/specialist-consultation")
@RestController
@RequiredArgsConstructor
public class SpecialistConsultationController {

    private final SpecialistConsultationService specialistConsultationService;

    @PostMapping("create-specialist-consultation")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createSpecialistConsultationRequest(@Nullable @RequestBody CreateSpecialistConsultationRequest body) {
        return specialistConsultationService.createSpecialistConsultationRequest(body);
    }

    @PostMapping("create-specialist-consultation-table")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createSpecialistConsultationAppointmentTableRequest(@Nullable @RequestBody CreateSpecialistConsultationAppointmentTableRequest body) {
        return specialistConsultationService.createSpecialistConsultationAppointmentTableRequest(body);

    }
}
