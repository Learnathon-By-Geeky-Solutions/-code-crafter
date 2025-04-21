package com.xenon.core.service.user;

import com.xenon.core.domain.request.hospital.CreateAppointmentTableRequest;
import com.xenon.core.domain.request.user.CreateAccountRequest;
import com.xenon.core.domain.request.user.UpdateAccountRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<?> createAccount(CreateAccountRequest body);

    ResponseEntity<?> update(UpdateAccountRequest body);

    ResponseEntity<?> getProfile();

}
