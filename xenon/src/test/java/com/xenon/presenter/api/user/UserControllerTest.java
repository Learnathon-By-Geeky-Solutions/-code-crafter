package com.xenon.presenter.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.user.CreateAccountRequest;
import com.xenon.core.domain.request.user.UpdateAccountRequest;
import com.xenon.core.domain.request.user.UpdateUserLatitudeLongitude;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.service.user.UserService;
import com.xenon.data.entity.user.Gender;
import com.xenon.data.entity.user.UserRole;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CreateAccountRequest createAccountRequest;
    private UpdateAccountRequest updateAccountRequest;
    private UpdateUserLatitudeLongitude updateLocationRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
        
        // Set up create account request
        createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setPhone("01712345678");
        createAccountRequest.setPassword("password123");
        createAccountRequest.setConfirmPassword("password123");
        
        // Set up update account request
        updateAccountRequest = new UpdateAccountRequest();
        updateAccountRequest.setFirstName("Jane");
        updateAccountRequest.setLastName("Smith");
        updateAccountRequest.setEmail("jane.smith@example.com");
        updateAccountRequest.setUpazilaId(1L);
        updateAccountRequest.setGender(Gender.FEMALE);
        updateAccountRequest.setArea("Test Area");
        
        // Set up update location request
        updateLocationRequest = new UpdateUserLatitudeLongitude();
        updateLocationRequest.setLatitude(23.8103);
        updateLocationRequest.setLongitude(90.4125);
    }

    @Test
    void createAccount() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Account created successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        doReturn(responseEntity).when(userService).createAccount(any(CreateAccountRequest.class), any(UserRole.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/user/sign-up")
                .param("role", "USER")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAccountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Account created successfully"));

        verify(userService).createAccount(any(CreateAccountRequest.class), eq(UserRole.USER));
    }

    @Test
    void updateProfile() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Account updated successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        doReturn(responseEntity).when(userService).update(any(UpdateAccountRequest.class));

        // Act & Assert
        mockMvc.perform(put("/api/v1/user/user-profile-update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateAccountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Account updated successfully"));

        verify(userService).update(any(UpdateAccountRequest.class));
    }

    @Test
    void updateLatitudeLongitude() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Location updated successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        doReturn(responseEntity).when(userService).updateLatitudeLongitude(any(UpdateUserLatitudeLongitude.class));

        // Act & Assert
        mockMvc.perform(put("/api/v1/user/user-profile-latitude-longitude-update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateLocationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Location updated successfully"));

        verify(userService).updateLatitudeLongitude(any(UpdateUserLatitudeLongitude.class));
    }

    @Test
    void getUserProfile() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "User profile has been retrieved successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        doReturn(responseEntity).when(userService).getProfile();

        // Act & Assert
        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("User profile has been retrieved successfully"));

        verify(userService).getProfile();
    }
}