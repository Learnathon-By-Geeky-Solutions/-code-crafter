package com.xenon.presenter.api.bloodBank;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.bloodBank.CreateBloodBankAccountRequest;
import com.xenon.core.service.bloodBank.BloodBankService;
import com.xenon.data.entity.user.UserRole;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/blood-bank")
@RequiredArgsConstructor
public class BloodBankController {

    private final BloodBankService bloodBankService;

    @PostMapping("create")
    @PreAuthorize(authorities = {UserRole.BLOOD_BANK, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> create(@Nullable @RequestBody CreateBloodBankAccountRequest body) {
        return bloodBankService.createBloodBankRequest(body);
    }
}
