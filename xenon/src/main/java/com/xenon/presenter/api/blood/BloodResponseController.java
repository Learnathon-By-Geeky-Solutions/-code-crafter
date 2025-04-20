package com.xenon.presenter.api.blood;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.blood.CreateBloodResponseRequest;
import com.xenon.core.service.blood.BloodResponseService;
import com.xenon.data.entity.user.UserRole;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/blood-response")
@RequiredArgsConstructor
public class BloodResponseController {

    private final BloodResponseService bloodResponseService;

    @PostMapping("create")
    @PreAuthorize(authorities = {UserRole.DOCTOR,UserRole.USER, UserRole.ADMIN}, shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createBloodResponse(@Nullable @RequestBody CreateBloodResponseRequest body) {
        return bloodResponseService.createBloodResponseRequest(body);
    }
}
