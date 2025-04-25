package com.xenon.core.service.hospital.appointment;

import com.xenon.core.domain.request.hospital.doctorBooking.OfflineAppointmentRequest;
import com.xenon.data.entity.doctor.SpecialistCategory;
import com.xenon.data.entity.hospital.DAY;
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
}