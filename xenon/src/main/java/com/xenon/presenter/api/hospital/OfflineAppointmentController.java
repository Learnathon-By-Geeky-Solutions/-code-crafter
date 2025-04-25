package com.xenon.presenter.api.hospital;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.hospital.doctorBooking.OfflineAppointmentRequest;
import com.xenon.core.service.hospital.appointment.OfflineAppointmentService;
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
@RequestMapping("/api/v1/offline-appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class OfflineAppointmentController {

    private final OfflineAppointmentService offlineAppointmentService;

    @PostMapping("/book")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> bookOfflineAppointment(@Nullable @RequestBody OfflineAppointmentRequest request) {
        return offlineAppointmentService.bookOfflineAppointment(request);
    }

    @GetMapping("/hospitals")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAvailableHospitals() {
        return offlineAppointmentService.getAvailableHospitals();
    }

    @GetMapping("/hospitals/{hospitalBranchId}/departments")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getHospitalDepartments(@PathVariable Long hospitalBranchId) {
        return offlineAppointmentService.getHospitalDepartments(hospitalBranchId);
    }

    @GetMapping("/hospitals/{hospitalBranchId}/doctors")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getDoctorsByDepartment(
            @PathVariable Long hospitalBranchId,
            @RequestParam(required = false) SpecialistCategory specialistCategory) {
        return offlineAppointmentService.getDoctorsByDepartment(hospitalBranchId, specialistCategory);
    }

    @GetMapping("/hospitals/{hospitalBranchId}/doctors/{doctorId}/schedules")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getDoctorSchedules(
            @PathVariable Long hospitalBranchId,
            @PathVariable Long doctorId) {
        return offlineAppointmentService.getDoctorSchedules(hospitalBranchId, doctorId);
    }

    @GetMapping("/schedules/{scheduleId}/slots")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAvailableSlots(
            @PathVariable Long scheduleId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return offlineAppointmentService.getAvailableSlots(scheduleId, date);
    }

    @GetMapping("/user")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getUserOfflineAppointments() {
        return offlineAppointmentService.getUserOfflineAppointments();
    }

    @GetMapping("/hospital/{hospitalBranchId}")
    @PreAuthorize(authorities = {UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getHospitalOfflineAppointments(@PathVariable Long hospitalBranchId) {
        return offlineAppointmentService.getHospitalOfflineAppointments(hospitalBranchId);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getDoctorOfflineAppointments(@PathVariable Long doctorId) {
        return offlineAppointmentService.getDoctorOfflineAppointments(doctorId);
    }

    @PostMapping("/cancel/{appointmentId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> cancelOfflineAppointment(@PathVariable Long appointmentId) {
        return offlineAppointmentService.cancelOfflineAppointment(appointmentId);
    }

    @PostMapping("/complete/{appointmentId}")
    @PreAuthorize(authorities = {UserRole.DOCTOR, UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> completeOfflineAppointment(@PathVariable Long appointmentId) {
        return offlineAppointmentService.completeOfflineAppointment(appointmentId);
    }
}