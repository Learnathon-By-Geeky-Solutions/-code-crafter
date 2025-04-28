package com.xenon.presenter.api.hospital;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.hospital.*;
import com.xenon.core.domain.request.hospital.doctorBooking.OfflineAppointmentRequest;
import com.xenon.core.service.hospital.HospitalService;
import com.xenon.core.service.hospital.appointment.OfflineAppointmentService;
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
@RequestMapping("/api/v1/hospitals")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
@Tag(name = "Hospital Management", description = "APIs for managing hospitals and offline appointments")
public class HospitalController {

    private final HospitalService hospitalService;
    private final OfflineAppointmentService offlineAppointmentService;

    // ----------------- HOSPITAL MANAGEMENT APIs -----------------

    @PostMapping("/create")
    @PreAuthorize(authorities = {UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create hospital account", description = "Register a new hospital")
    public ResponseEntity<?> createHospital(
            @Parameter(description = "Hospital account details")
            @Nullable @RequestBody CreateHospitalAccountRequest body) {
        return hospitalService.CreateHospitalAccountRequest(body);
    }

    @PostMapping("/branches/create")
    @PreAuthorize(authorities = {UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create hospital branch", description = "Add a new branch for an existing hospital")
    public ResponseEntity<?> createHospitalBranch(
            @Parameter(description = "Hospital branch details")
            @Nullable @RequestBody CreateHospitalBranchAccountRequest body) {
        return hospitalService.createHospitalBranchAccountRequest(body);
    }

    @PostMapping("/doctors/affiliations/create")
    @PreAuthorize(authorities = {UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create doctor affiliation", description = "Associate a doctor with a hospital branch")
    public ResponseEntity<?> createDoctorAffiliation(
            @Parameter(description = "Doctor affiliation details")
            @Nullable @RequestBody CreateOfflineDoctorAffiliationRequest body) {
        return hospitalService.createOfflineDoctorAffiliationRequest(body);
    }

    @PostMapping("/schedules/create")
    @PreAuthorize(authorities = {UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create doctor schedule", description = "Set up a schedule for an affiliated doctor")
    public ResponseEntity<?> createDoctorSchedule(
            @Parameter(description = "Doctor schedule details")
            @Nullable @RequestBody CreateDoctorScheduleRequest body) {
        return hospitalService.createDoctorScheduleRequest(body);
    }

    // ----------------- OFFLINE APPOINTMENTS APIs -----------------

    @GetMapping
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get all hospitals", description = "List all available hospitals")
    public ResponseEntity<?> getAllHospitals() {
        return offlineAppointmentService.getAvailableHospitals();
    }

    @GetMapping("/{hospitalBranchId}/departments")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get hospital departments", description = "List all departments in a hospital branch")
    public ResponseEntity<?> getHospitalDepartments(
            @Parameter(description = "Hospital branch ID") @PathVariable Long hospitalBranchId) {
        return offlineAppointmentService.getHospitalDepartments(hospitalBranchId);
    }

    @GetMapping("/{hospitalBranchId}/doctors")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get doctors by department", description = "List doctors in a specific department at a hospital branch")
    public ResponseEntity<?> getDoctorsByDepartment(
            @Parameter(description = "Hospital branch ID") @PathVariable Long hospitalBranchId,
            @Parameter(description = "Optional filter by specialist category")
            @RequestParam(required = false) SpecialistCategory specialistCategory) {
        return offlineAppointmentService.getDoctorsByDepartment(hospitalBranchId, specialistCategory);
    }

    @GetMapping("/{hospitalBranchId}/doctors/{doctorId}/schedules")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get doctor schedules", description = "List schedules for a doctor at a specific hospital branch")
    public ResponseEntity<?> getDoctorSchedules(
            @Parameter(description = "Hospital branch ID") @PathVariable Long hospitalBranchId,
            @Parameter(description = "Doctor ID") @PathVariable Long doctorId) {
        return offlineAppointmentService.getDoctorSchedules(hospitalBranchId, doctorId);
    }

    @GetMapping("/schedules/{scheduleId}/slots")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get available slots", description = "List available appointment slots for a doctor schedule")
    public ResponseEntity<?> getAvailableSlots(
            @Parameter(description = "Schedule ID") @PathVariable Long scheduleId,
            @Parameter(description = "Date for slot availability")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return offlineAppointmentService.getAvailableSlots(scheduleId, date);
    }

    @PostMapping("/appointments/book")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Book offline appointment", description = "Book an appointment with a doctor at a hospital")
    public ResponseEntity<?> bookOfflineAppointment(
            @Parameter(description = "Offline appointment booking details")
            @Nullable @RequestBody OfflineAppointmentRequest request) {
        return offlineAppointmentService.bookOfflineAppointment(request);
    }

    @GetMapping("/appointments/user")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get user's offline appointments", description = "List all offline appointments for the current user")
    public ResponseEntity<?> getUserOfflineAppointments() {
        return offlineAppointmentService.getUserOfflineAppointments();
    }

    @GetMapping("/{hospitalBranchId}/appointments")
    @PreAuthorize(authorities = {UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get hospital's offline appointments", description = "List all offline appointments for a hospital branch")
    public ResponseEntity<?> getHospitalOfflineAppointments(
            @Parameter(description = "Hospital branch ID") @PathVariable Long hospitalBranchId) {
        return offlineAppointmentService.getHospitalOfflineAppointments(hospitalBranchId);
    }

    @GetMapping("/doctors/{doctorId}/appointments")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get doctor's offline appointments", description = "List all offline appointments for a doctor")
    public ResponseEntity<?> getDoctorOfflineAppointments(
            @Parameter(description = "Doctor ID") @PathVariable Long doctorId) {
        return offlineAppointmentService.getDoctorOfflineAppointments(doctorId);
    }

    @PostMapping("/appointments/{appointmentId}/cancel")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cancel offline appointment", description = "Cancel a pending or confirmed offline appointment")
    public ResponseEntity<?> cancelOfflineAppointment(
            @Parameter(description = "Appointment ID") @PathVariable Long appointmentId) {
        return offlineAppointmentService.cancelOfflineAppointment(appointmentId);
    }

    @PostMapping("/appointments/{appointmentId}/complete")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Complete offline appointment", description = "Mark an offline appointment as completed")
    public ResponseEntity<?> completeOfflineAppointment(
            @Parameter(description = "Appointment ID") @PathVariable Long appointmentId) {
        return offlineAppointmentService.completeOfflineAppointment(appointmentId);
    }

    @PostMapping("/appointments/{appointmentId}/confirm")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Confirm offline appointment", description = "Confirm a pending offline appointment")
    public ResponseEntity<?> confirmOfflineAppointment(
            @Parameter(description = "Appointment ID") @PathVariable Long appointmentId) {
        return offlineAppointmentService.confirmOfflineAppointment(appointmentId);
    }
}