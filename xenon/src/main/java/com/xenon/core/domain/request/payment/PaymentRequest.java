package com.xenon.core.domain.request.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String paymentMethod;
    private Double amount;
    private String currency;
    private String returnUrl;
    private String consultationType; // EMERGENCY, SPECIALIST, OFFLINE
    private Long consultationId;
}