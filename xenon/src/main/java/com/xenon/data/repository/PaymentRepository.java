package com.xenon.data.repository;

import com.xenon.data.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    Optional<Payment> findByPaymentId(String paymentId);
    
    List<Payment> findByUserId(Long userId);
    
    List<Payment> findByConsultationTypeAndEmergencyAppointmentId(String consultationType, Long appointmentId);
    
    List<Payment> findByConsultationTypeAndSpecialistAppointmentId(String consultationType, Long appointmentId);
    
    List<Payment> findByConsultationTypeAndOfflineAppointmentId(String consultationType, Long appointmentId);
}