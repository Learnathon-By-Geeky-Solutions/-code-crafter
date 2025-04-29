package com.xenon.core.service.user;

import com.xenon.core.domain.request.hospital.CreateAppointmentTableRequest;
import com.xenon.core.domain.request.user.CreateAccountRequest;
import com.xenon.core.domain.request.user.UpdateAccountRequest;
import com.xenon.core.domain.request.user.UpdateUserLatitudeLongitude;
import com.xenon.data.entity.user.UserRole;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<?> createAccount(CreateAccountRequest body, UserRole role);

    ResponseEntity<?> update(UpdateAccountRequest body);

    ResponseEntity<?> getProfile();

    ResponseEntity<?> updateLatitudeLongitude(UpdateUserLatitudeLongitude body);
}
