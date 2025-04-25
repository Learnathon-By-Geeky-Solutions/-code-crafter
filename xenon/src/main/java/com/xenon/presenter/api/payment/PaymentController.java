/*
package com.xenon.presenter.api.payment;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.payment.PaymentRequest;
import com.xenon.core.service.payment.PaymentService;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createPayment(@Nullable @RequestBody PaymentRequest request) {
        return paymentService.createPaymentIntent(request);
    }

    @PostMapping("/confirm/{paymentId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> confirmPayment(@PathVariable String paymentId) {
        return paymentService.confirmPayment(paymentId);
    }

    @GetMapping("/status/{paymentId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String paymentId) {
        return paymentService.getPaymentStatus(paymentId);
    }

    @PostMapping("/cancel/{paymentId}")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> cancelPayment(@PathVariable String paymentId) {
        return paymentService.cancelPayment(paymentId);
    }
}*/
