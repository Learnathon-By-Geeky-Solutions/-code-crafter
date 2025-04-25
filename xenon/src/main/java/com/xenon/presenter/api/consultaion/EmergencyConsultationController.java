package com.xenon.presenter.api.consultaion;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.consultation.emergency.CreateEmergencyConsultationRequest;
import com.xenon.core.domain.request.consultation.emergency.EmergencyConsultationAppointmentRequest;
import com.xenon.core.service.consultation.emergency.EmergencyConsultationService;
import com.xenon.data.entity.doctor.SpecialistCategory;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emergency-consultation")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class EmergencyConsultationController {

    private final EmergencyConsultationService emergencyConsultationService;

    @PostMapping("/create")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createEmergencyConsultation(@Nullable @RequestBody CreateEmergencyConsultationRequest request) {
        return emergencyConsultationService.createEmergencyConsultation(request);
    }

    @PostMapping("/book")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> bookEmergencyConsultation(@Nullable @RequestBody EmergencyConsultationAppointmentRequest request) {
        return emergencyConsultationService.bookEmergencyConsultation(request);
    }

    @GetMapping("/available-doctors")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAvailableDoctors(@RequestParam(required = false) SpecialistCategory specialistCategory) {
        return emergencyConsultationService.getAvailableDoctors(specialistCategory);
    }

    @PostMapping("/toggle-availability/{doctorId}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> toggleDoctorAvailability(@PathVariable Long doctorId) {
        return emergencyConsultationService.toggleDoctorAvailability(doctorId);
    }

    @GetMapping("/{consultationId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getEmergencyConsultationDetails(@PathVariable Long consultationId) {
        return emergencyConsultationService.getEmergencyConsultationDetails(consultationId);
    }

    @GetMapping("/user")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getUserEmergencyConsultations() {
        return emergencyConsultationService.getUserEmergencyConsultations();
    }

    @GetMapping("/doctor")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getDoctorEmergencyConsultations() {
        return emergencyConsultationService.getDoctorEmergencyConsultations();
    }

    @PostMapping("/cancel/{appointmentId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> cancelEmergencyConsultation(@PathVariable Long appointmentId) {
        return emergencyConsultationService.cancelEmergencyConsultation(appointmentId);
    }

    @PostMapping("/complete/{appointmentId}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> completeEmergencyConsultation(@PathVariable Long appointmentId) {
        return emergencyConsultationService.completeEmergencyConsultation(appointmentId);
    }
}