package com.xenon.core.service.user;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.request.user.CreateAccountRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.user.User;
import com.xenon.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl extends BaseService implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> createAccount(CreateAccountRequest body) {
        validateCreateAccountRequest(body);

        try {
            userRepository.save(body.toEntity(passwordEncoder));
            return success("Account created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> update() {
        User user = getCurrentUser();
        return ResponseEntity.ok("Hello World");
    }

    @Override
    public ResponseEntity<?> getProfile() {
        User user = getCurrentUser();
        return success("User profile has been retrieved successfully", getCurrentUser().toResponse());
    }

    private void validateCreateAccountRequest(CreateAccountRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getPhone())) throw requiredField("phone");
        if (!PHONE_PATTERN.matcher(body.getPhone()).matches()) throw clientException("Invalid phone number");
        if (userRepository.existsByPhone(body.getPhone())) throw clientException("Phone number already exists!");
        if (isNullOrBlank(body.getPassword())) throw requiredField("password");
        if (body.getPassword().length() < 6 || body.getPassword().length() > 32) throw clientException("Password must be between 6 and 32 characters");
        if (isNullOrBlank(body.getConfirmPassword())) throw requiredField("confirmPassword");
        if (!Objects.equals(body.getPassword(), body.getConfirmPassword())) throw clientException("Passwords do not match");
    }
}
