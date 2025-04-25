/*
package com.xenon.core.service.payment;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.payment.PaymentRequest;
import com.xenon.core.domain.response.payment.PaymentResponse;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.consultation.emergency.EmergencyConsultationAppointmentTable;
import com.xenon.data.entity.consultation.specialist.SpecialistConsultationAppointmentTable;
import com.xenon.data.entity.hospital.APPOINTMENT_STATUS;
import com.xenon.data.entity.hospital.offlineBooking.OfflineAppointmentTable;
import com.xenon.data.entity.payment.Payment;
import com.xenon.data.repository.EmergencyConsultationTableRepository;
import com.xenon.data.repository.OfflineAppointmentTableRepository;
import com.xenon.data.repository.PaymentRepository;
import com.xenon.data.repository.SpecialistConsultationAppointmentTableRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripePaymentService extends BaseService implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    private final PaymentRepository paymentRepository;
    private final EmergencyConsultationTableRepository emergencyConsultationTableRepository;
    private final SpecialistConsultationAppointmentTableRepository specialistConsultationAppointmentTableRepository;
    private final OfflineAppointmentTableRepository offlineAppointmentTableRepository;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    @Override
    @Transactional
    public ResponseEntity<?> createPaymentIntent(PaymentRequest paymentRequest) {
        validatePaymentRequest(paymentRequest);

        try {
            // Convert amount to cents (Stripe uses smallest currency unit)
            long amountInCents = Math.round(paymentRequest.getAmount() * 100);

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setCurrency(paymentRequest.getCurrency().toLowerCase())
                    .setAmount(amountInCents)
                    .setReceiptEmail(getCurrentUserEmail())
                    .setDescription("Payment for consultation")
                    .putMetadata("consultation_type", paymentRequest.getConsultationType())
                    .putMetadata("consultation_id", paymentRequest.getConsultationId().toString())
                    .putMetadata("user_id", getCurrentUser().getId().toString())
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // Save payment record
            Payment payment = new Payment();
            payment.setUser(getCurrentUser());
            payment.setPaymentId(paymentIntent.getId());
            payment.setAmount(paymentRequest.getAmount());
            payment.setCurrency(paymentRequest.getCurrency());
            payment.setStatus("PENDING");
            payment.setPaymentMethod(paymentRequest.getPaymentMethod());
            payment.setConsultationType(paymentRequest.getConsultationType());

            // Set the appropriate consultation ID based on type
            switch (paymentRequest.getConsultationType()) {
                case "EMERGENCY":
                    payment.setEmergencyAppointmentId(paymentRequest.getConsultationId());
                    updateEmergencyAppointmentPayment(paymentRequest.getConsultationId(), paymentIntent.getId());
                    break;
                case "SPECIALIST":
                    payment.setSpecialistAppointmentId(paymentRequest.getConsultationId());
                    updateSpecialistAppointmentPayment(paymentRequest.getConsultationId(), paymentIntent.getId());
                    break;
                case "OFFLINE":
                    payment.setOfflineAppointmentId(paymentRequest.getConsultationId());
                    updateOfflineAppointmentPayment(paymentRequest.getConsultationId(), paymentIntent.getId());
                    break;
                default:
                    throw new ClientException("Invalid consultation type");
            }

            paymentRepository.save(payment);

            PaymentResponse response = new PaymentResponse();
            response.setPaymentId(paymentIntent.getId());
            response.setPaymentUrl(paymentIntent.getClientSecret());
            response.setStatus("PENDING");
            response.setAmount(paymentRequest.getAmount());
            response.setCurrency(paymentRequest.getCurrency());

            return success("Payment intent created successfully", response);
        } catch (StripeException e) {
            log.error("Stripe error: {}", e.getMessage(), e);
            throw new ApiException(e);
        } catch (Exception e) {
            log.error("Error creating payment intent: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> confirmPayment(String paymentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentId);
            
            if ("succeeded".equals(paymentIntent.getStatus())) {
                // Update payment status in our database
                Payment payment = paymentRepository.findByPaymentId(paymentId)
                        .orElseThrow(() -> new ClientException("Payment not found"));
                
                payment.setStatus("COMPLETED");
                paymentRepository.save(payment);
                
                // Update appointment status based on consultation type
                switch (payment.getConsultationType()) {
                    case "EMERGENCY":
                        completeEmergencyAppointment(payment.getEmergencyAppointmentId());
                        break;
                    case "SPECIALIST":
                        completeSpecialistAppointment(payment.getSpecialistAppointmentId());
                        break;
                    case "OFFLINE":
                        completeOfflineAppointment(payment.getOfflineAppointmentId());
                        break;
                }
                
                return success("Payment confirmed successfully", null);
            } else {
                throw new ClientException("Payment has not been completed");
            }
        } catch (StripeException e) {
            log.error("Stripe error: {}", e.getMessage(), e);
            throw new ApiException(e);
        } catch (Exception e) {
            log.error("Error confirming payment: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getPaymentStatus(String paymentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentId);
            
            PaymentResponse response = new PaymentResponse();
            response.setPaymentId(paymentIntent.getId());
            response.setStatus(mapStripeStatusToInternal(paymentIntent.getStatus()));
            response.setAmount(paymentIntent.getAmount() / 100.0); // Convert cents to dollars
            response.setCurrency(paymentIntent.getCurrency().toUpperCase());
            
            return success("Payment status retrieved successfully", response);
        } catch (StripeException e) {
            log.error("Stripe error: {}", e.getMessage(), e);
            throw new ApiException(e);
        } catch (Exception e) {
            log.error("Error getting payment status: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> cancelPayment(String paymentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentId);
            
            Map<String, Object> params = new HashMap<>();
            params.put("cancellation_reason", "requested_by_customer");
            
            PaymentIntent canceledIntent = paymentIntent.cancel(params);
            
            // Update our database
            Payment payment = paymentRepository.findByPaymentId(paymentId)
                    .orElseThrow(() -> new ClientException("Payment not found"));
            
            payment.setStatus("CANCELLED");
            paymentRepository.save(payment);
            
            // Cancel the appointment
            switch (payment.getConsultationType()) {
                case "EMERGENCY":
                    cancelEmergencyAppointment(payment.getEmergencyAppointmentId());
                    break;
                case "SPECIALIST":
                    cancelSpecialistAppointment(payment.getSpecialistAppointmentId());
                    break;
                case "OFFLINE":
                    cancelOfflineAppointment(payment.getOfflineAppointmentId());
                    break;
            }
            
            return success("Payment cancelled successfully", null);
        } catch (StripeException e) {
            log.error("Stripe error: {}", e.getMessage(), e);
            throw new ApiException(e);
        } catch (Exception e) {
            log.error("Error cancelling payment: {}", e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void validatePaymentRequest(PaymentRequest paymentRequest) {
        super.validateBody(paymentRequest);
        
        if (paymentRequest.getAmount() == null || paymentRequest.getAmount() <= 0) {
            throw new ClientException("Invalid amount");
        }
        
        if (isNullOrBlank(paymentRequest.getCurrency())) {
            throw new ClientException("Currency is required");
        }
        
        if (isNullOrBlank(paymentRequest.getConsultationType())) {
            throw new ClientException("Consultation type is required");
        }
        
        if (paymentRequest.getConsultationId() == null) {
            throw new ClientException("Consultation ID is required");
        }
        
        // Validate that the consultation exists based on type
        switch (paymentRequest.getConsultationType()) {
            case "EMERGENCY":
                emergencyConsultationTableRepository.findById(paymentRequest.getConsultationId())
                        .orElseThrow(() -> new ClientException("Emergency consultation not found"));
                break;
            case "SPECIALIST":
                specialistConsultationAppointmentTableRepository.findById(paymentRequest.getConsultationId())
                        .orElseThrow(() -> new ClientException("Specialist consultation not found"));
                break;
            case "OFFLINE":
                offlineAppointmentTableRepository.findById(paymentRequest.getConsultationId())
                        .orElseThrow(() -> new ClientException("Offline appointment not found"));
                break;
            default:
                throw new ClientException("Invalid consultation type");
        }
    }

    private String mapStripeStatusToInternal(String stripeStatus) {
        switch (stripeStatus) {
            case "succeeded":
                return "COMPLETED";
            case "processing":
            case "requires_action":
            case "requires_confirmation":
            case "requires_payment_method":
                return "PENDING";
            case "canceled":
                return "CANCELLED";
            default:
                return "FAILED";
        }
    }

    private void updateEmergencyAppointmentPayment(Long appointmentId, String paymentId) {
        EmergencyConsultationAppointmentTable appointment = emergencyConsultationTableRepository.findById(appointmentId)
                .orElseThrow(() -> new ClientException("Emergency consultation not found"));
        
        appointment.setPaymentId(paymentId);
        appointment.setPaymentStatus("PENDING");
        
        emergencyConsultationTableRepository.save(appointment);
    }

    private void updateSpecialistAppointmentPayment(Long appointmentId, String paymentId) {
        SpecialistConsultationAppointmentTable appointment = specialistConsultationAppointmentTableRepository.findById(appointmentId)
                .orElseThrow(() -> new ClientException("Specialist consultation not found"));
        
        appointment.setPaymentId(paymentId);
        appointment.setPaymentStatus("PENDING");
        
        specialistConsultationAppointmentTableRepository.save(appointment);
    }

    private void updateOfflineAppointmentPayment(Long appointmentId, String paymentId) {
        OfflineAppointmentTable appointment = offlineAppointmentTableRepository.findById(appointmentId)
                .orElseThrow(() -> new ClientException("Offline appointment not found"));
        
        appointment.setPaymentId(paymentId);
        appointment.setPaymentStatus("PENDING");
        
        offlineAppointmentTableRepository.save(appointment);
    }
    
    private void completeEmergencyAppointment(Long appointmentId) {
        EmergencyConsultationAppointmentTable appointment = emergencyConsultationTableRepository.findById(appointmentId)
                .orElseThrow(() -> new ClientException("Emergency consultation not found"));
        
        appointment.setAppointment_status(APPOINTMENT_STATUS.CONFIRMED);
        appointment.setPaymentStatus("COMPLETED");
        
        // Generate Google Meet link
        String meetLink = generateGoogleMeetLink();
        appointment.setMeetingLink(meetLink);
        
        emergencyConsultationTableRepository.save(appointment);
        
        // Set doctor's availability to unavailable temporarily
        appointment.getEmergencyConsultation().setAvailability(com.xenon.data.entity.hospital.AVAILABILITY.UNAVAILABLE);
        
        // Send notifications to user and doctor
        sendNotification(appointment.getUser().getId(), "Emergency Consultation Confirmed",
                "Your emergency consultation has been confirmed. Please join using the meeting link: " + meetLink);
        
        sendNotification(appointment.getEmergencyConsultation().getDoctor().getUser().getId(),
                "New Emergency Consultation",
                "You have a new emergency consultation. Please join using the meeting link: " + meetLink);
    }
    
    private void completeSpecialistAppointment(Long appointmentId) {
        SpecialistConsultationAppointmentTable appointment = specialistConsultationAppointmentTableRepository.findById(appointmentId)
                .orElseThrow(() -> new ClientException("Specialist consultation not found"));
        
        appointment.setAppointment_status(APPOINTMENT_STATUS.CONFIRMED);
        appointment.setPaymentStatus("COMPLETED");
        
        // Generate Google Meet link
        String meetLink = generateGoogleMeetLink();
        appointment.setMeetingLink(meetLink);
        
        specialistConsultationAppointmentTableRepository.save(appointment);
        
        // Send notifications to user and doctor
        sendNotification(appointment.getUser().getId(), "Specialist Consultation Confirmed",
                "Your specialist consultation has been confirmed for " + 
                appointment.getConsultationDate() + ". Please join using the meeting link: " + meetLink);
        
        sendNotification(appointment.getSpecialistConsultation().getDoctor().getUser().getId(),
                "New Specialist Consultation",
                "You have a new specialist consultation scheduled for " + 
                appointment.getConsultationDate() + ". Please join using the meeting link: " + meetLink);
    }
    
    private void completeOfflineAppointment(Long appointmentId) {
        OfflineAppointmentTable appointment = offlineAppointmentTableRepository.findById(appointmentId)
                .orElseThrow(() -> new ClientException("Offline appointment not found"));
        
        appointment.setAppointment_status(APPOINTMENT_STATUS.CONFIRMED);
        appointment.setPaymentStatus("COMPLETED");
        
        offlineAppointmentTableRepository.save(appointment);
        
        // Send notifications to user and hospital
        sendNotification(appointment.getUser().getId(), "Offline Appointment Confirmed",
                "Your offline appointment has been confirmed for " + 
                appointment.getAppointmentDate() + " at " + appointment.getAppointmentTime());
        
        sendNotification(appointment.getDoctorSchedule().getOfflineDoctorAffiliation().getHospitalBranch().getHospital().getUser().getId(),
                "New Offline Appointment",
                "You have a new offline appointment scheduled for " + 
                appointment.getAppointmentDate() + " at " + appointment.getAppointmentTime());
    }
    
    private void cancelEmergencyAppointment(Long appointmentId) {
        EmergencyConsultationAppointmentTable appointment = emergencyConsultationTableRepository.findById(appointmentId)
                .orElseThrow(() -> new ClientException("Emergency consultation not found"));
        
        appointment.setAppointment_status(APPOINTMENT_STATUS.CANCELLED);
        appointment.setPaymentStatus("CANCELLED");
        
        emergencyConsultationTableRepository.save(appointment);
        
        // Set doctor's availability back to available
        appointment.getEmergencyConsultation().setAvailability(com.xenon.data.entity.hospital.AVAILABILITY.AVAILABLE);
    }
    
    private void cancelSpecialistAppointment(Long appointmentId) {
        SpecialistConsultationAppointmentTable appointment = specialistConsultationAppointmentTableRepository.findById(appointmentId)
                .orElseThrow(() -> new ClientException("Specialist consultation not found"));
        
        appointment.setAppointment_status(APPOINTMENT_STATUS.CANCELLED);
        appointment.setPaymentStatus("CANCELLED");
        
        specialistConsultationAppointmentTableRepository.save(appointment);
    }
    
    private void cancelOfflineAppointment(Long appointmentId) {
        OfflineAppointmentTable appointment = offlineAppointmentTableRepository.findById(appointmentId)
                .orElseThrow(() -> new ClientException("Offline appointment not found"));
        
        appointment.setAppointment_status(APPOINTMENT_STATUS.CANCELLED);
        appointment.setPaymentStatus("CANCELLED");
        
        offlineAppointmentTableRepository.save(appointment);
    }
    
    private String generateGoogleMeetLink() {
        // For now, generate a dummy Google Meet link
        // In production, this should integrate with Google Calendar API
        return "https://meet.google.com/" + generateRandomCode();
    }
    
    private String generateRandomCode() {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int index = (int) (chars.length() * Math.random());
                sb.append(chars.charAt(index));
            }
            if (i < 2) {
                sb.append("-");
            }
        }
        return sb.toString();
    }
    
    private void sendNotification(Long userId, String title, String message) {
        // In a real implementation, this would send the notification through a service
        // For now, we'll just log it
        log.info("Notification for user {}: {} - {}", userId, title, message);
        
        // Here you would typically save to the notification table
        // and possibly trigger a real-time notification through WebSockets
    }*/
