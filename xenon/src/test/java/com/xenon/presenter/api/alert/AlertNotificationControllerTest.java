package com.xenon.presenter.api.alert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.alert.UpdateUserLocationRequest;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.domain.response.alert.AlertResponse;
import com.xenon.core.domain.response.alert.UserAlertNotificationResponse;
import com.xenon.core.service.alert.AlertNotificationServiceUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AlertNotificationControllerTest {

    @Mock
    private AlertNotificationServiceUser alertNotificationService;

    @InjectMocks
    private AlertNotificationController alertNotificationController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private final Long NOTIFICATION_ID = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(alertNotificationController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // For handling dates
    }

    @Test
    void getUserNotifications_ShouldReturnNotificationList() throws Exception {
        // Arrange
        List<UserAlertNotificationResponse> notifications = new ArrayList<>();
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Notifications retrieved successfully", notifications);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(alertNotificationService).getCurrentUserAlertNotifications(any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/alert/notifications")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "createdAt")
                .param("direction", "desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Notifications retrieved successfully"));

        // Verify
        Pageable expectedPageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        verify(alertNotificationService).getCurrentUserAlertNotifications(any(Pageable.class));
    }

    @Test
    void getUnreadNotifications_ShouldReturnUnreadNotifications() throws Exception {
        // Arrange
        List<UserAlertNotificationResponse> notifications = new ArrayList<>();
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Unread notifications retrieved successfully", notifications);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(alertNotificationService).getUnreadAlertNotifications();

        // Act & Assert
        mockMvc.perform(get("/api/v1/alert/notifications/unread")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Unread notifications retrieved successfully"));

        // Verify
        verify(alertNotificationService).getUnreadAlertNotifications();
    }

    @Test
    void markNotificationAsRead_ShouldMarkNotificationAsRead() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Notification marked as read", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(alertNotificationService).markNotificationAsRead(anyLong());

        // Act & Assert
        mockMvc.perform(put("/api/v1/alert/notifications/{notificationId}/read", NOTIFICATION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Notification marked as read"));

        // Verify
        verify(alertNotificationService).markNotificationAsRead(NOTIFICATION_ID);
    }

    @Test
    void markAllNotificationsAsRead_ShouldMarkAllNotificationsAsRead() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "All notifications marked as read", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(alertNotificationService).markAllNotificationsAsRead();

        // Act & Assert
        mockMvc.perform(put("/api/v1/alert/notifications/read-all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("All notifications marked as read"));

        // Verify
        verify(alertNotificationService).markAllNotificationsAsRead();
    }

    @Test
    void getNearbyAlerts_ShouldReturnNearbyAlerts() throws Exception {
        // Arrange
        List<AlertResponse> alerts = new ArrayList<>();
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Nearby alerts retrieved successfully", alerts);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(alertNotificationService).getNearbyAlerts();

        // Act & Assert
        mockMvc.perform(get("/api/v1/alert/nearby")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Nearby alerts retrieved successfully"));

        // Verify
        verify(alertNotificationService).getNearbyAlerts();
    }
}