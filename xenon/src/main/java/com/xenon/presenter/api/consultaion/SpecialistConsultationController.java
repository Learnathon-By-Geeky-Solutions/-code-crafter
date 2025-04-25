package com.xenon.presenter.api.consultaion;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.consultation.specialist.CreateSpecialistConsultationRequest;
import com.xenon.core.domain.request.consultation.specialist.SpecialistConsultationAppointmentRequest;
import com.xenon.core.service.consultation.specialist.SpecialistConsultationService;
import com.xenon.data.entity.doctor.SpecialistCategory;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/specialist-consultation")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class SpecialistConsultationController {

    private final SpecialistConsultationService specialistConsultationService;

    @PostMapping("/create")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createSpecialistConsultation(@Nullable @RequestBody CreateSpecialistConsultationRequest request) {
        return specialistConsultationService.createSpecialistConsultation(request);
    }

    @PostMapping("/book")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> bookSpecialistConsultation(@Nullable @RequestBody SpecialistConsultationAppointmentRequest request) {
        return specialistConsultationService.bookSpecialistConsultation(request);
    }

    @GetMapping("/available-doctors")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAvailableDoctors(@RequestParam(required = false) SpecialistCategory specialistCategory) {
        return specialistConsultationService.getAvailableDoctors(specialistCategory);
    }

    @GetMapping("/available-slots/{doctorId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAvailableSlots(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return specialistConsultationService.getAvailableSlots(doctorId, date);
    }

    @GetMapping("/{consultationId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getSpecialistConsultationDetails(@PathVariable Long consultationId) {
        return specialistConsultationService.getSpecialistConsultationDetails(consultationId);
    }

    @GetMapping("/user")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getUserSpecialistConsultations() {
        return specialistConsultationService.getUserSpecialistConsultations();
    }

    @GetMapping("/doctor")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getDoctorSpecialistConsultations() {
        return specialistConsultationService.getDoctorSpecialistConsultations();
    }

    @PostMapping("/cancel/{appointmentId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> cancelSpecialistConsultation(@PathVariable Long appointmentId) {
        return specialistConsultationService.cancelSpecialistConsultation(appointmentId);
    }

    @PostMapping("/complete/{appointmentId}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> completeSpecialistConsultation(@PathVariable Long appointmentId) {
        return specialistConsultationService.completeSpecialistConsultation(appointmentId);
    }

    @PostMapping("/toggle-availability/{consultationId}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> toggleConsultationAvailability(@PathVariable Long consultationId) {
        return specialistConsultationService.toggleConsultationAvailability(consultationId);
    }
}