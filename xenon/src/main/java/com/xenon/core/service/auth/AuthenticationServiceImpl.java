package com.xenon.core.service.auth;

import com.xenon.common.util.JwtUtil;
import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.AuthException;
import com.xenon.core.domain.request.auth.LoginRequest;
import com.xenon.core.service.common.BaseService;
import com.xenon.data.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl extends BaseService implements AuthenticationService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> login(LoginRequest body) {
        validateLoginRequest(body);

        try {
            String token = jwtUtil.generateAccessToken(body.getPhone());
            return success("Logged in successfully", token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void validateLoginRequest(LoginRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getPhone())) throw requiredField("phone");
        if (!PHONE_PATTERN.matcher(body.getPhone()).matches()) throw clientException("Invalid phone number");
        User user = userRepository.findByPhone(body.getPhone()).orElseThrow(() -> new AuthException("Phone number does not exist!"));
//        if (!passwordEncoder.matches(body.getPassword(), user.getPassword())) throw clientException("Incorrect password entered");
        if (!Objects.equals(body.getPassword(), user.getPassword())) throw clientException("Incorrect password entered");
        if (isNullOrBlank(body.getPassword())) throw requiredField("password");
    }
}
