package com.xenon.core.service.common;

import com.xenon.core.domain.exception.ClientException;
import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.user.Gender;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Slf4j
public abstract class AppointmentBaseService extends BaseService {
    
    /**
     * Validates beneficiary information
     * @param isBeneficiary Whether this is for a beneficiary
     * @param name Beneficiary name
     * @param phone Beneficiary phone
     * @param address Beneficiary address
     * @param gender Beneficiary gender
     * @param age Beneficiary age
     */
    protected void validateBeneficiaryInfo(Boolean isBeneficiary, String name, String phone, 
                                          String address, Gender gender, Integer age) {
        if (isBeneficiary == null) {
            throw new ClientException("Beneficiary information is required");
        }

        if (isBeneficiary) {
            if (isNullOrBlank(name)) {
                throw new ClientException("Beneficiary name is required");
            }

            if (isNullOrBlank(phone)) {
                throw new ClientException("Beneficiary phone is required");
            }

            if (isNullOrBlank(address)) {
                throw new ClientException("Beneficiary address is required");
            }

            if (gender == null) {
                throw new ClientException("Beneficiary gender is required");
            }

            if (age == null || age <= 0) {
                throw new ClientException("Valid beneficiary age is required");
            }
        }
    }
    
    /**
     * Validates appointment date and time
     * @param date Appointment date
     * @param time Appointment time (if applicable)
     */
    protected void validateAppointmentDateTime(LocalDate date, LocalTime time) {
        if (date == null) {
            throw new ClientException("Appointment date is required");
        }

        // Ensure the appointment date is not in the past
        if (date.isBefore(LocalDate.now())) {
            throw new ClientException("Appointment date must be in the future");
        }

        // If time is provided and appointment is today, ensure the time is in the future
        if (time != null && date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now())) {
            throw new ClientException("Appointment time must be in the future");
        }
    }
    
    /**
     * Generates a meeting link for online consultations
     * @return A Google Meet-like link
     */
    protected String generateMeetingLink() {
        // In a real application, this would integrate with Google Meet API
        // For now, just generate a random UUID-based link
        return "https://meet.google.com/" + UUID.randomUUID().toString().substring(0, 8);
    }
    
    /**
     * Checks if an appointment can be cancelled
     * @param currentStatus Current appointment status
     */
    protected void validateCancellation(AppointmentStatus currentStatus) {
        if (currentStatus == AppointmentStatus.CANCELLED) {
            throw new ClientException("Appointment is already cancelled");
        }
    }
    
    /**
     * Checks if an appointment can be completed
     * @param currentStatus Current appointment status
     */
    protected void validateCompletion(AppointmentStatus currentStatus) {
        if (currentStatus == AppointmentStatus.CANCELLED) {
            throw new ClientException("Cannot complete a cancelled appointment");
        }
    }
    
    /**
     * Checks if an appointment can be confirmed
     * @param currentStatus Current appointment status
     */
    protected void validateConfirmation(AppointmentStatus currentStatus) {
        if (currentStatus != AppointmentStatus.PENDING) {
            throw new ClientException("Can only confirm appointments in pending status");
        }
    }
}