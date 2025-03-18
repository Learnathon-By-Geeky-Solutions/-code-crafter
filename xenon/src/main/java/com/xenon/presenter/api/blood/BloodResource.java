package com.xenon.presenter.api.blood;

import com.xenon.core.domain.request.DonorRequest;
import com.xenon.core.service.blood.BloodService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/blood")
@RequiredArgsConstructor
public class BloodResource {

    private final BloodService bloodService;

    @PostMapping("create")
    public ResponseEntity<?> create(@Nullable DonorRequest body) {
        return bloodService.createDonorRequest(body);
    }
}
