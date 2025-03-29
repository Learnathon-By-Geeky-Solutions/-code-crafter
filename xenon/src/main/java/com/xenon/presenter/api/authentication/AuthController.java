package com.xenon.presenter.api.authentication;

import com.xenon.core.domain.request.auth.LoginRequest;
import com.xenon.core.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<?> login(@Nullable @RequestBody LoginRequest body) {
        return authenticationService.login(body);
    }
}
