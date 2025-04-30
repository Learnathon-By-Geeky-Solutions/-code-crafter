/*
package com.xenon.core.service.hospital.appointment;

import com.xenon.core.domain.request.hospital.CreateDoctorScheduleRequest;
import com.xenon.core.domain.request.hospital.doctorBooking.OfflineAppointmentRequest;
import com.xenon.data.entity.doctor.SpecialistCategory;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface OfflineAppointmentService {

    ResponseEntity<?> bookOfflineAppointment(OfflineAppointmentRequest request);

    ResponseEntity<?> getAvailableHospitals();

    ResponseEntity<?> getHospitalDepartments(Long hospitalBranchId);

    ResponseEntity<?> getDoctorsByDepartment(Long hospitalBranchId, SpecialistCategory specialistCategory);

    ResponseEntity<?> getDoctorSchedules(Long hospitalBranchId, Long doctorId);

    ResponseEntity<?> getAvailableSlots(Long scheduleId, LocalDate date);

    ResponseEntity<?> getUserOfflineAppointments();

    ResponseEntity<?> getHospitalOfflineAppointments(Long hospitalBranchId);

    ResponseEntity<?> getDoctorOfflineAppointments(Long doctorId);

    ResponseEntity<?> cancelOfflineAppointment(Long appointmentId);

    ResponseEntity<?> completeOfflineAppointment(Long appointmentId);

    */
/**
     * Confirms a pending offline appointment
     * @param appointmentId The appointment ID
     * @return ResponseEntity with success/failure message
     *//*

    ResponseEntity<?> confirmOfflineAppointment(Long appointmentId);

    */
/**
     * Creates a new doctor schedule for offline appointments
     * @param request The schedule creation request
     * @return ResponseEntity with success/failure message
     *//*

    ResponseEntity<?> createDoctorSchedule(CreateDoctorScheduleRequest request);
}*/
