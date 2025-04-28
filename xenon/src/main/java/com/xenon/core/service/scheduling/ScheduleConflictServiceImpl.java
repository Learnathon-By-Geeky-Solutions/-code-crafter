package com.xenon.core.service.scheduling;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.service.common.BaseService;
import com.xenon.data.entity.consultation.emergency.EmergencyConsultation;
import com.xenon.data.entity.consultation.specialist.SpecialistConsultation;
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.hospital.AVAILABILITY;
import com.xenon.data.entity.hospital.DAY;
import com.xenon.data.entity.hospital.DoctorSchedule;
import com.xenon.data.repository.DoctorRepository;
import com.xenon.data.repository.DoctorScheduleRepository;
import com.xenon.data.repository.EmergencyConsultationRepository;
import com.xenon.data.repository.SpecialistConsultationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleConflictServiceImpl extends BaseService implements ScheduleConflictService {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final SpecialistConsultationRepository specialistConsultationRepository;
    private final EmergencyConsultationRepository emergencyConsultationRepository;

    @Override
    public ResponseEntity<?> checkSpecialistScheduleConflict(Long doctorId, DAY day, LocalTime startTime, LocalTime endTime) {
        try {
            // First verify the doctor exists
            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new ClientException("Doctor not found"));

            // Check offline schedule conflicts
            checkOfflineScheduleConflicts(doctorId, day, startTime, endTime);

            // Check specialist schedule conflicts
            checkSpecialistScheduleConflicts(doctorId, day, startTime, endTime);

            // Check emergency consultation availability
            checkEmergencyConsultationAvailability(doctor);

            return success("No schedule conflicts found", null);
        }  catch (Exception e) {
            log.error("Error checking specialist schedule conflict: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> checkOfflineScheduleConflict(Long doctorId, Long hospitalBranchId, DAY day, LocalTime startTime, LocalTime endTime) {
        try {
            // First verify the doctor exists
            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new ClientException("Doctor not found"));

            // Check offline schedule conflicts
            checkOfflineScheduleConflicts(doctorId, day, startTime, endTime);

            // Check specialist schedule conflicts
            checkSpecialistScheduleConflicts(doctorId, day, startTime, endTime);

            // Check emergency consultation availability
            checkEmergencyConsultationAvailability(doctor);

            return success("No schedule conflicts found", null);
        } catch (Exception e) {
            log.error("Error checking offline schedule conflict: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> disableSpecialistConsultationsForEmergency(Long doctorId) {
        try {
            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new ClientException("Doctor not found"));

            // Get all specialist consultations for this doctor
            List<SpecialistConsultation> consultations = specialistConsultationRepository.findByDoctor(doctor);

            // Set all to unavailable
            for (SpecialistConsultation consultation : consultations) {
                consultation.setAvailability(AVAILABILITY.UNAVAILABLE);
                specialistConsultationRepository.save(consultation);
            }

            return success("All specialist consultations disabled for emergency consultation", null);
        } catch (Exception e) {
            log.error("Error disabling specialist consultations: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> disableEmergencyConsultationForSpecialist(Long doctorId) {
        try {
            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new ClientException("Doctor not found"));

            // Get emergency consultation for this doctor
            Optional<EmergencyConsultation> emergencyConsultationOpt = emergencyConsultationRepository.findByDoctor(doctor);

            if (emergencyConsultationOpt.isPresent()) {
                EmergencyConsultation emergencyConsultation = emergencyConsultationOpt.get();
                emergencyConsultation.setAvailability(AVAILABILITY.UNAVAILABLE);
                emergencyConsultationRepository.save(emergencyConsultation);
            }

            return success("Emergency consultation disabled for specialist consultation", null);
        } catch (Exception e) {
            log.error("Error disabling emergency consultation: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public boolean doTimesOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        // Two time ranges overlap if one starts before the other ends and ends after the other starts
        return (start1.isBefore(end2) && start2.isBefore(end1));
    }

    /**
     * Checks for conflicts with offline schedules
     */
    private void checkOfflineScheduleConflicts(Long doctorId, DAY day, LocalTime startTime, LocalTime endTime) {
        List<DoctorSchedule> offlineSchedules = doctorScheduleRepository.findByOfflineDoctorAffiliation_DoctorIdAndDay(doctorId, day);

        for (DoctorSchedule schedule : offlineSchedules) {
            if (doTimesOverlap(startTime, endTime, schedule.getStartTime(), schedule.getEndTime())) {
                throw new ClientException(
                        "Schedule Conflict: This time slot conflicts with an offline appointment schedule at " +
                                schedule.getOfflineDoctorAffiliation().getHospitalBranch().getBranchName()
                );
            }
        }
    }

    /**
     * Checks for conflicts with specialist schedules
     */
    private void checkSpecialistScheduleConflicts(Long doctorId, DAY day, LocalTime startTime, LocalTime endTime) {
        List<SpecialistConsultation> consultations = specialistConsultationRepository.findByDoctorIdAndDay(doctorId, day);

        for (SpecialistConsultation consultation : consultations) {
            if (doTimesOverlap(startTime, endTime, consultation.getStartTime(), consultation.getEndTime())) {
                throw new ClientException("Schedule Conflict: This time slot conflicts with a specialist consultation schedule");
            }
        }
    }

    /**
     * Checks if emergency consultation is active
     */
    private void checkEmergencyConsultationAvailability(Doctor doctor) {
        Optional<EmergencyConsultation> emergencyConsultation = emergencyConsultationRepository.findByDoctor(doctor);

        if (emergencyConsultation.isPresent() && emergencyConsultation.get().getAvailability() == AVAILABILITY.AVAILABLE) {
            throw new ClientException("Schedule Conflict: Doctor has an active emergency consultation");
        }
    }
}