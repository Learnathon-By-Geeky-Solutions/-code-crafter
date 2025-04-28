package com.xenon.core.service.scheduling;

import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import com.xenon.data.entity.hospital.DAY;

public interface ScheduleConflictService {
    
    /**
     * Checks if there are any scheduling conflicts for a specialist consultation
     * @param doctorId The doctor ID
     * @param day The day of the week
     * @param startTime The start time
     * @param endTime The end time
     * @return ResponseEntity with conflict status and message
     */
    ResponseEntity<?> checkSpecialistScheduleConflict(Long doctorId, DAY day, LocalTime startTime, LocalTime endTime);
    
    /**
     * Checks if there are any scheduling conflicts for an offline doctor schedule
     * @param doctorId The doctor ID
     * @param hospitalBranchId The hospital branch ID
     * @param day The day of the week
     * @param startTime The start time
     * @param endTime The end time
     * @return ResponseEntity with conflict status and message
     */
    ResponseEntity<?> checkOfflineScheduleConflict(Long doctorId, Long hospitalBranchId, DAY day, LocalTime startTime, LocalTime endTime);
    
    /**
     * Disables all specialist consultations when emergency consultation is enabled
     * @param doctorId The doctor ID
     * @return ResponseEntity with success/failure message
     */
    ResponseEntity<?> disableSpecialistConsultationsForEmergency(Long doctorId);
    
    /**
     * Disables emergency consultation when specialist consultation is enabled
     * @param doctorId The doctor ID
     * @return ResponseEntity with success/failure message
     */
    ResponseEntity<?> disableEmergencyConsultationForSpecialist(Long doctorId);
    
    /**
     * Checks if two time ranges overlap
     * @param start1 Start time of first range
     * @param end1 End time of first range
     * @param start2 Start time of second range
     * @param end2 End time of second range
     * @return true if there is an overlap, false otherwise
     */
    boolean doTimesOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2);
}