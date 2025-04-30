package com.xenon.presenter.api.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.alert.UpdateUserLocationRequest;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.service.location.LocationService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UpdateUserLocationRequest locationRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
        objectMapper = new ObjectMapper();

        // Set up location request
        locationRequest = new UpdateUserLocationRequest();
        locationRequest.setLatitude(23.8103);
        locationRequest.setLongitude(90.4125);
        locationRequest.setLocationAllowed(true);
    }

    @Test
    void updateLocation_Success() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Location updated successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        doReturn(responseEntity).when(locationService).updateUserLocation(any(UpdateUserLocationRequest.class));

        // Act & Assert
        mockMvc.perform(put("/api/v1/location/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(locationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Location updated successfully"));

        verify(locationService).updateUserLocation(any(UpdateUserLocationRequest.class));
    }

    @Test
    void updateLocation_InvalidLocation() throws Exception {
        // Arrange
        locationRequest.setLatitude(95.0); // Invalid latitude (> 90)
        
        BaseResponse<Object> errorResponse = new BaseResponse<>("XE0009", "Latitude must be between -90 and 90 degrees", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.badRequest().body(errorResponse);
        doReturn(responseEntity).when(locationService).updateUserLocation(any(UpdateUserLocationRequest.class));

        // Act & Assert
        mockMvc.perform(put("/api/v1/location/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(locationRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("XE0009"))
                .andExpect(jsonPath("$.message").value("Latitude must be between -90 and 90 degrees"));

        verify(locationService).updateUserLocation(any(UpdateUserLocationRequest.class));
    }
}