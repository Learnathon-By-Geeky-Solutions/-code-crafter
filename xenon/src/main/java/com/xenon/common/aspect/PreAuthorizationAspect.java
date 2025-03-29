package com.xenon.common.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.common.annotation.PreAuthorize;
import com.xenon.common.util.JwtUtil;
import com.xenon.core.domain.exception.AuthException;
import com.xenon.core.domain.exception.UnauthorizedException;
import com.xenon.core.domain.model.ResponseMessage;
import com.xenon.data.entity.user.User;
import com.xenon.data.repository.UserRepository;
import com.xenon.presenter.config.ApplicationConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
@Aspect
@RequiredArgsConstructor
public class PreAuthorizationAspect {

    private static final Logger log = LoggerFactory.getLogger(PreAuthorizationAspect.class);
    private final HttpServletRequest request;
    private final JwtUtil jwtUtil;
    private final UserRepository repository;
    private final ObjectMapper objectMapper;

    @Pointcut("@annotation(preAuthorize)")
    public void preAuthorizationAspect(PreAuthorize preAuthorize) {}

    @Before(value = "preAuthorizationAspect(preAuthorize)", argNames = "preAuthorize")
    public void checkAuthorization(@NonNull PreAuthorize preAuthorize) {
        final String authHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Objects.isNull(authHeaderValue) || authHeaderValue.isBlank()) {
            throw new AuthException(ResponseMessage.AUTH_HEADER_MISSING);
        }

        String[] tokenParts = authHeaderValue.split(" ");
        if (tokenParts.length != 2 || !tokenParts[0].equals("Bearer")) {
            throw new AuthException(ResponseMessage.AUTH_HEADER_MISMATCH);
        }

        String token = tokenParts[1];
        if (jwtUtil.isExpired(token)) {
            throw new AuthException(ResponseMessage.SESSION_EXPIRED);
        }

        User user = repository.findByPhone(jwtUtil.getCurrentUserPhone(token)).orElseThrow(() -> new UnauthorizedException("No user found in the system with the provided user id"));

        boolean hasPermission = Arrays.stream(preAuthorize.authorities()).anyMatch(authority -> authority.equals(user.getRole()));

        if (!hasPermission) {
            throw new UnauthorizedException("You do not have permission to access this resource");
        }

        if (preAuthorize.shouldCheckAccountStatus()) {
            switch (user.getStatus()) {
                case INACTIVE:
                    throw new UnauthorizedException("Your account is inactive. Please contact the admin to activate your account.");
                case BANNED:
                    throw new UnauthorizedException("Your account is suspended. Please contact the admin to resolve the issue.");
            }
        }


        try {
            request.setAttribute(ApplicationConfig.USER_REQUEST_ATTRIBUTE_KEY, objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new AuthException(e.getMessage());
        }
    }
}
