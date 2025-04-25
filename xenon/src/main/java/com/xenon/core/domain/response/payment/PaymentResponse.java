package com.xenon.core.domain.response.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String paymentId;
    private String paymentUrl;
    private String status;
    private Double amount;
    private String currency;
}