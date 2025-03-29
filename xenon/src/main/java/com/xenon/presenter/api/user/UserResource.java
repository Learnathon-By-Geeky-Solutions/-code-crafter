package com.xenon.presenter.api.user;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.user.CreateAccountRequest;
import com.xenon.core.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @PostMapping("sign-up")
    public ResponseEntity<?> createAccount(@Nullable @RequestBody CreateAccountRequest body) {
        return userService.createAccount(body);
    }

    @PutMapping
    @PreAuthorize
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateProfile() {
        return userService.update();
    }

    @GetMapping
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getUserProfile() {
        return userService.getProfile();
    }
}
