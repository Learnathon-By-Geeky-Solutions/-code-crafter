package com.xenon.core.service.payment;

import com.xenon.core.domain.request.payment.PaymentRequest;
import com.xenon.core.domain.response.payment.PaymentResponse;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    
    ResponseEntity<?> createPaymentIntent(PaymentRequest paymentRequest);
    
    ResponseEntity<?> confirmPayment(String paymentId);
    
    ResponseEntity<?> getPaymentStatus(String paymentId);
    
    ResponseEntity<?> cancelPayment(String paymentId);
}