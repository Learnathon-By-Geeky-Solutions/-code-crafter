/*
package com.xenon.presenter.api.consultaion;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.consultation.emergency.CreateEmergencyConsultationRequest;
import com.xenon.core.domain.request.consultation.emergency.EmergencyConsultationAppointmentRequest;
import com.xenon.core.domain.request.consultation.specialist.CreateSpecialistConsultationRequest;
import com.xenon.core.domain.request.consultation.specialist.SpecialistConsultationAppointmentRequest;
import com.xenon.core.service.consultation.emergency.EmergencyConsultationService;
import com.xenon.core.service.consultation.specialist.SpecialistConsultationService;
import com.xenon.data.entity.doctor.SpecialistCategory;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/consultations")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
@Tag(name = "Consultation Management", description = "APIs for managing online consultations")
public class ConsultationController {

    private final EmergencyConsultationService emergencyConsultationService;
//    private final SpecialistConsultationService specialistConsultationService;

    // ----------------- EMERGENCY CONSULTATION APIs -----------------

    @PostMapping("/emergency/create")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create emergency consultation service", description = "Doctors can create emergency consultation service")
    public ResponseEntity<?> createEmergencyConsultation(
            @Parameter(description = "Emergency consultation details")
            @Nullable @RequestBody CreateEmergencyConsultationRequest request) {
        return emergencyConsultationService.createEmergencyConsultation(request);
    }

    @PostMapping("/emergency/book")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Book emergency consultation", description = "Users can book an emergency consultation with available doctors")
    public ResponseEntity<?> bookEmergencyConsultation(
            @Parameter(description = "Emergency consultation booking details")
            @Nullable @RequestBody EmergencyConsultationAppointmentRequest request) {
        return emergencyConsultationService.bookEmergencyConsultation(request);
    }

    @GetMapping("/emergency/available-doctors")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get available doctors for emergency consultation", description = "List doctors available for emergency consultations")
    public ResponseEntity<?> getAvailableEmergencyDoctors(
            @Parameter(description = "Optional filter by specialist category")
            @RequestParam(required = false) SpecialistCategory specialistCategory) {
        return emergencyConsultationService.getAvailableDoctors(specialistCategory);
    }

    @PostMapping("/emergency/toggle-availability/{doctorId}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Toggle doctor availability for emergency consultations", description = "Enable/disable a doctor's availability for emergency consultations")
    public ResponseEntity<?> toggleEmergencyAvailability(
            @Parameter(description = "Doctor ID") @PathVariable Long doctorId) {
        return emergencyConsultationService.toggleDoctorAvailability(doctorId);
    }

    @GetMapping("/emergency/{consultationId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get emergency consultation details", description = "View details of a specific emergency consultation")
    public ResponseEntity<?> getEmergencyConsultationDetails(
            @Parameter(description = "Consultation ID") @PathVariable Long consultationId) {
        return emergencyConsultationService.getEmergencyConsultationDetails(consultationId);
    }

    @GetMapping("/emergency/user")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get user's emergency consultations", description = "List all emergency consultations for the current user")
    public ResponseEntity<?> getUserEmergencyConsultations() {
        return emergencyConsultationService.getUserEmergencyConsultations();
    }

    @GetMapping("/emergency/doctor")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get doctor's emergency consultations", description = "List all emergency consultations for the current doctor")
    public ResponseEntity<?> getDoctorEmergencyConsultations() {
        return emergencyConsultationService.getDoctorEmergencyConsultations();
    }

    @PostMapping("/emergency/cancel/{appointmentId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cancel emergency consultation", description = "Cancel a pending or confirmed emergency consultation")
    public ResponseEntity<?> cancelEmergencyConsultation(
            @Parameter(description = "Appointment ID") @PathVariable Long appointmentId) {
        return emergencyConsultationService.cancelEmergencyConsultation(appointmentId);
    }

    @PostMapping("/emergency/complete/{appointmentId}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Complete emergency consultation", description = "Mark an emergency consultation as completed")
    public ResponseEntity<?> completeEmergencyConsultation(
            @Parameter(description = "Appointment ID") @PathVariable Long appointmentId) {
        return emergencyConsultationService.completeEmergencyConsultation(appointmentId);
    }

    @PostMapping("/emergency/confirm/{appointmentId}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Confirm emergency consultation", description = "Confirm a pending emergency consultation")
    public ResponseEntity<?> confirmEmergencyConsultation(
            @Parameter(description = "Appointment ID") @PathVariable Long appointmentId) {
        return emergencyConsultationService.confirmEmergencyConsultation(appointmentId);
    }

    */
/*//*
/ ----------------- SPECIALIST CONSULTATION APIs -----------------

    @PostMapping("/specialist/create")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create specialist consultation service", description = "Doctors can create specialist consultation schedules")
    public ResponseEntity<?> createSpecialistConsultation(
            @Parameter(description = "Specialist consultation details") 
            @Nullable @RequestBody CreateSpecialistConsultationRequest request) {
        return specialistConsultationService.createSpecialistConsultation(request);
    }

    @PostMapping("/specialist/book")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Book specialist consultation", description = "Users can book a specialist consultation in an available time slot")
    public ResponseEntity<?> bookSpecialistConsultation(
            @Parameter(description = "Specialist consultation booking details") 
            @Nullable @RequestBody SpecialistConsultationAppointmentRequest request) {
        return specialistConsultationService.bookSpecialistConsultation(request);
    }

    @GetMapping("/specialist/available-doctors")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get available doctors for specialist consultation", description = "List doctors available for specialist consultations")
    public ResponseEntity<?> getAvailableSpecialistDoctors(
            @Parameter(description = "Optional filter by specialist category")
            @RequestParam(required = false) SpecialistCategory specialistCategory) {
        return specialistConsultationService.getAvailableDoctors(specialistCategory);
    }

    @GetMapping("/specialist/available-slots/{doctorId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get available time slots", description = "List available time slots for a doctor on a specific date")
    public ResponseEntity<?> getAvailableSpecialistSlots(
            @Parameter(description = "Doctor ID") @PathVariable Long doctorId,
            @Parameter(description = "Date for slot availability") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return specialistConsultationService.getAvailableSlots(doctorId, date);
    }

    @GetMapping("/specialist/{consultationId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get specialist consultation details", description = "View details of a specific specialist consultation")
    public ResponseEntity<?> getSpecialistConsultationDetails(
            @Parameter(description = "Consultation ID") @PathVariable Long consultationId) {
        return specialistConsultationService.getSpecialistConsultationDetails(consultationId);
    }

    @GetMapping("/specialist/user")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get user's specialist consultations", description = "List all specialist consultations for the current user")
    public ResponseEntity<?> getUserSpecialistConsultations() {
        return specialistConsultationService.getUserSpecialistConsultations();
    }

    @GetMapping("/specialist/doctor")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get doctor's specialist consultations", description = "List all specialist consultations for the current doctor")
    public ResponseEntity<?> getDoctorSpecialistConsultations() {
        return specialistConsultationService.getDoctorSpecialistConsultations();
    }

    @PostMapping("/specialist/cancel/{appointmentId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cancel specialist consultation", description = "Cancel a pending or confirmed specialist consultation")
    public ResponseEntity<?> cancelSpecialistConsultation(
            @Parameter(description = "Appointment ID") @PathVariable Long appointmentId) {
        return specialistConsultationService.cancelSpecialistConsultation(appointmentId);
    }

    @PostMapping("/specialist/complete/{appointmentId}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Complete specialist consultation", description = "Mark a specialist consultation as completed")
    public ResponseEntity<?> completeSpecialistConsultation(
            @Parameter(description = "Appointment ID") @PathVariable Long appointmentId) {
        return specialistConsultationService.completeSpecialistConsultation(appointmentId);
    }

    @PostMapping("/specialist/toggle-availability/{consultationId}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Toggle specialist consultation availability", description = "Enable/disable a specialist consultation schedule")
    public ResponseEntity<?> toggleSpecialistAvailability(
            @Parameter(description = "Consultation ID") @PathVariable Long consultationId) {
        return specialistConsultationService.toggleConsultationAvailability(consultationId);
    }

    @PostMapping("/specialist/confirm/{appointmentId}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Confirm specialist consultation", description = "Confirm a pending specialist consultation")
    public ResponseEntity<?> confirmSpecialistConsultation(
            @Parameter(description = "Appointment ID") @PathVariable Long appointmentId) {
        return specialistConsultationService.confirmSpecialistConsultation(appointmentId);
    }*//*

}
*/
