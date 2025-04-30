package com.xenon.common.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.common.annotation.PreAuthorize;
import com.xenon.common.util.JwtUtil;
import com.xenon.core.domain.exception.AuthException;
import com.xenon.core.domain.exception.UnauthorizedException;
import com.xenon.core.domain.model.ResponseMessage;
import com.xenon.data.entity.user.AccountStatus;
import com.xenon.data.entity.user.User;
import com.xenon.data.entity.user.UserRole;
import com.xenon.data.repository.UserRepository;
import com.xenon.presenter.config.ApplicationConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PreAuthorizationAspectTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private JoinPoint joinPoint;

    @InjectMocks
    private PreAuthorizationAspect authorizationAspect;

    private User adminUser;
    private User regularUser;
    private User bannedUser;
    private PreAuthorize preAuthorize;
    private PreAuthorize preAuthorizeWithCheck;

    @BeforeEach
    void setUp() throws Exception {
        // Set up users
        adminUser = new User();
        adminUser.setId(1L);
        adminUser.setPhone("01711111111");
        adminUser.setRole(UserRole.ADMIN);
        adminUser.setStatus(AccountStatus.ACTIVE);

        regularUser = new User();
        regularUser.setId(2L);
        regularUser.setPhone("01722222222");
        regularUser.setRole(UserRole.USER);
        regularUser.setStatus(AccountStatus.ACTIVE);

        bannedUser = new User();
        bannedUser.setId(3L);
        bannedUser.setPhone("01733333333");
        bannedUser.setRole(UserRole.USER);
        bannedUser.setStatus(AccountStatus.BANNED);

        // Set up PreAuthorize annotation with different configurations
        preAuthorize = createPreAuthorize(new UserRole[]{UserRole.ADMIN}, false);
        preAuthorizeWithCheck = createPreAuthorize(new UserRole[]{UserRole.USER, UserRole.ADMIN}, true);
    }

    private PreAuthorize createPreAuthorize(UserRole[] roles, boolean checkStatus) {
        return new PreAuthorize() {
            @Override
            public Class<PreAuthorize> annotationType() {
                return PreAuthorize.class;
            }

            @Override
            public UserRole[] authorities() {
                return roles;
            }

            @Override
            public boolean shouldCheckAccountStatus() {
                return checkStatus;
            }
        };
    }

    @Test
    void checkAuthorization_ValidAdminUser() throws Exception {
        // Arrange
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer validToken");
        when(jwtUtil.isExpired(anyString())).thenReturn(false);
        when(jwtUtil.getCurrentUserPhone(anyString())).thenReturn("01711111111");
        when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(adminUser));
        when(objectMapper.writeValueAsString(any(User.class))).thenReturn("{\"id\":1,\"role\":\"ADMIN\"}");

        // Act - Should not throw exception
        authorizationAspect.checkAuthorization(preAuthorize);

        // Assert
        verify(request).setAttribute(ApplicationConfig.USER_REQUEST_ATTRIBUTE_KEY, "{\"id\":1,\"role\":\"ADMIN\"}");
    }

    @Test
    void checkAuthorization_MissingAuthHeader() {
        // Arrange
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        // Act & Assert
        AuthException exception = assertThrows(AuthException.class, () -> {
            authorizationAspect.checkAuthorization(preAuthorize);
        });
        assertEquals(ResponseMessage.AUTH_HEADER_MISSING, exception.getResponseMessage());
    }

    @Test
    void checkAuthorization_InvalidAuthHeaderFormat() {
        // Arrange
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("InvalidFormat");

        // Act & Assert
        AuthException exception = assertThrows(AuthException.class, () -> {
            authorizationAspect.checkAuthorization(preAuthorize);
        });
        assertEquals(ResponseMessage.AUTH_HEADER_MISMATCH, exception.getResponseMessage());
    }

    @Test
    void checkAuthorization_ExpiredToken() {
        // Arrange
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer expiredToken");
        when(jwtUtil.isExpired(anyString())).thenReturn(true);

        // Act & Assert
        AuthException exception = assertThrows(AuthException.class, () -> {
            authorizationAspect.checkAuthorization(preAuthorize);
        });
        assertEquals(ResponseMessage.SESSION_EXPIRED, exception.getResponseMessage());
    }

    @Test
    void checkAuthorization_UserNotFound() {
        // Arrange
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer validToken");
        when(jwtUtil.isExpired(anyString())).thenReturn(false);
        when(jwtUtil.getCurrentUserPhone(anyString())).thenReturn("nonexistent");
        when(userRepository.findByPhone(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> {
            authorizationAspect.checkAuthorization(preAuthorize);
        });
    }

    @Test
    void checkAuthorization_InsufficientPermissions() {
        // Arrange
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer validToken");
        when(jwtUtil.isExpired(anyString())).thenReturn(false);
        when(jwtUtil.getCurrentUserPhone(anyString())).thenReturn("01722222222");
        when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(regularUser));

        // Act & Assert - Regular user trying to access admin endpoint
        assertThrows(UnauthorizedException.class, () -> {
            authorizationAspect.checkAuthorization(preAuthorize);
        });
    }

    @Test
    void checkAuthorization_BannedUserWithStatusCheck() {
        // Arrange
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer validToken");
        when(jwtUtil.isExpired(anyString())).thenReturn(false);
        when(jwtUtil.getCurrentUserPhone(anyString())).thenReturn("01733333333");
        when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(bannedUser));

        // Act & Assert - Banned user with status check enabled
        assertThrows(UnauthorizedException.class, () -> {
            authorizationAspect.checkAuthorization(preAuthorizeWithCheck);
        });
    }
}