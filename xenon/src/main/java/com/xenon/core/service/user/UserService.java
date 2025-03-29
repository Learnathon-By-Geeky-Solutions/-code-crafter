package com.xenon.core.service.user;

import com.xenon.core.domain.request.user.CreateAccountRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> createAccount(CreateAccountRequest body);

    ResponseEntity<?> update();

    ResponseEntity<?> getProfile();
}
