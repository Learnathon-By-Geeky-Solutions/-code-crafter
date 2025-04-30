package com.xenon.presenter.api.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.auth.LoginRequest;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.service.auth.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();

        // Set up login request
        loginRequest = new LoginRequest();
        loginRequest.setPhone("01711111111");
        loginRequest.setPassword("password123");
    }

    @Test
    void login_Success() throws Exception {
        // Arrange
        BaseResponse<String> successResponse = new BaseResponse<>("XS0001", "Logged in successfully", "jwt.token.here");
        ResponseEntity<BaseResponse<String>> responseEntity = ResponseEntity.ok(successResponse);
        doReturn(responseEntity).when(authenticationService).login(any(LoginRequest.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Logged in successfully"))
                .andExpect(jsonPath("$.data").value("jwt.token.here"));

        verify(authenticationService).login(any(LoginRequest.class));
    }
}