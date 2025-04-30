package com.xenon.presenter.api.healthAuthorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.healthAuthorization.CreateAlertRequest;
import com.xenon.core.domain.request.healthAuthorization.CreateHealthAuthorizationAccountRequest;
import com.xenon.core.domain.request.healthAuthorization.UpdateAlertRequest;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.service.healthAuthorization.HealthAuthorizationService;
import com.xenon.data.entity.alert.AlertSeverity;
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

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class HealthAuthorizationControllerTest {

    @Mock
    private HealthAuthorizationService healthAuthorizationService;

    @InjectMocks
    private HealthAuthorizationController healthAuthorizationController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CreateHealthAuthorizationAccountRequest createAccountRequest;
    private CreateAlertRequest createAlertRequest;
    private UpdateAlertRequest updateAlertRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(healthAuthorizationController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // To handle ZonedDateTime

        // Set up create account request
        createAccountRequest = new CreateHealthAuthorizationAccountRequest();
        createAccountRequest.setAuthorizationNumber("AUTH12345");

        // Set up create alert request
        createAlertRequest = new CreateAlertRequest();
        createAlertRequest.setTitle("COVID-19 Alert");
        createAlertRequest.setDescription("Increased cases in your area");
        createAlertRequest.setAlertness("Please wear masks and maintain social distancing");
        createAlertRequest.setLatitude(23.8103);
        createAlertRequest.setLongitude(90.4125);
        createAlertRequest.setRadius(5.0);
        createAlertRequest.setSeverityLevel(AlertSeverity.HIGH);
        createAlertRequest.setStartDate(ZonedDateTime.now());
        createAlertRequest.setEndDate(ZonedDateTime.now().plusDays(7));

        // Set up update alert request
        updateAlertRequest = new UpdateAlertRequest();
        updateAlertRequest.setTitle("Updated COVID-19 Alert");
        updateAlertRequest.setDescription("Updated: Increased cases in your area");
        updateAlertRequest.setSeverityLevel(AlertSeverity.CRITICAL);
        updateAlertRequest.setIsActive(true);
    }

    @Test
    void createHealthAuthorizationAccount() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Health Authorization created successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        doReturn(responseEntity).when(healthAuthorizationService).createHealthAuthorizationRequest(any(CreateHealthAuthorizationAccountRequest.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/health-authorization/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAccountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Health Authorization created successfully"));

        verify(healthAuthorizationService).createHealthAuthorizationRequest(any(CreateHealthAuthorizationAccountRequest.class));
    }

    @Test
    void createAlert() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Alert created successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        doReturn(responseEntity).when(healthAuthorizationService).createAlertRequest(any(CreateAlertRequest.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/health-authorization/alert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAlertRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Alert created successfully"));

        verify(healthAuthorizationService).createAlertRequest(any(CreateAlertRequest.class));
    }

    @Test
    void updateAlert() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Alert updated successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        doReturn(responseEntity).when(healthAuthorizationService).updateAlertRequest(anyLong(), any(UpdateAlertRequest.class));

        // Act & Assert
        mockMvc.perform(put("/api/v1/health-authorization/alert/{alertId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateAlertRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Alert updated successfully"));

        verify(healthAuthorizationService).updateAlertRequest(eq(1L), any(UpdateAlertRequest.class));
    }

    @Test
    void deactivateAlert() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Alert deactivated successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        doReturn(responseEntity).when(healthAuthorizationService).deactivateAlert(anyLong());

        // Act & Assert
        mockMvc.perform(put("/api/v1/health-authorization/alert/{alertId}/deactivate", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Alert deactivated successfully"));

        verify(healthAuthorizationService).deactivateAlert(eq(1L));
    }

    @Test
    void deleteAlert() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Alert deleted successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        doReturn(responseEntity).when(healthAuthorizationService).deleteAlert(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/api/v1/health-authorization/alert/{alertId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Alert deleted successfully"));

        verify(healthAuthorizationService).deleteAlert(eq(1L));
    }

    @Test
    void getAllAlerts() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Alerts retrieved successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        doReturn(responseEntity).when(healthAuthorizationService).getAllAlerts();

        // Act & Assert
        mockMvc.perform(get("/api/v1/health-authorization/alerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Alerts retrieved successfully"));

        verify(healthAuthorizationService).getAllAlerts();
    }

    @Test
    void getAlertById() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Alert retrieved successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        doReturn(responseEntity).when(healthAuthorizationService).getAlertById(anyLong());

        // Act & Assert
        mockMvc.perform(get("/api/v1/health-authorization/alert/{alertId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Alert retrieved successfully"));

        verify(healthAuthorizationService).getAlertById(eq(1L));
    }
}