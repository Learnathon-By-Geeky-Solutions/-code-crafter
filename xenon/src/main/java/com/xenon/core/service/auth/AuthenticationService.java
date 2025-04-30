package com.xenon.core.service.auth;

import com.xenon.core.domain.request.auth.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> login(LoginRequest body);
}
