package com.xenon.presenter.api.hospital;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.hospital.*;
import com.xenon.core.service.hospital.HospitalService;
import com.xenon.data.entity.user.UserRole;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/hospital")
@RestController
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @PostMapping("create")
    @PreAuthorize(authorities = {UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> create(@Nullable @RequestBody CreateHospitalAccountRequest body) {
        return hospitalService.CreateHospitalAccountRequest(body);
    }

    @PostMapping("create-branch")
    @PreAuthorize(authorities = {UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createHospitalBranchAccount(@Nullable @RequestBody CreateHospitalBranchAccountRequest body) {
        return hospitalService.createHospitalBranchAccountRequest(body);
    }

    @PostMapping("create-offline-doctor-affiliation")
    @PreAuthorize(authorities = {UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createDoctorAffiliationRequest(@Nullable @RequestBody CreateOfflineDoctorAffiliationRequest body) {
        return hospitalService.createOfflineDoctorAffiliationRequest(body);
    }

    @PostMapping("create-doctor-schedule")
    @PreAuthorize(authorities = {UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createOfflineScheduleRequest(@Nullable @RequestBody CreateDoctorScheduleRequest body) {
        return hospitalService.createDoctorScheduleRequest(body);
    }

    @PostMapping("create-offline-appointment-table")
    @PreAuthorize(authorities = {UserRole.HOSPITAL, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createOfflineAppointmentTable(@Nullable @RequestBody CreateOfflineAppointmentTable body) {
        return hospitalService.createOfflineAppointmentTable(body);
    }
}
