package com.xenon.presenter.api.ambulance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.ambulance.AmbulanceReviewRequest;
import com.xenon.core.domain.request.ambulance.CreateAmbulanceAccountRequest;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.service.ambulance.AmbulanceService;
import com.xenon.data.entity.ambulance.AmbulanceStatus;
import com.xenon.data.entity.ambulance.AmbulanceType;
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
class AmbulanceControllerTest {

    @Mock
    private AmbulanceService ambulanceService;

    @InjectMocks
    private AmbulanceController ambulanceController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CreateAmbulanceAccountRequest createRequest;
    private AmbulanceReviewRequest reviewRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ambulanceController).build();
        objectMapper = new ObjectMapper();

        // Set up create request
        createRequest = new CreateAmbulanceAccountRequest();
        createRequest.setAmbulanceType(AmbulanceType.ICU);
        createRequest.setAmbulanceNumber("AMB-TEST-123");
        createRequest.setAmbulanceStatus(AmbulanceStatus.AVAILABLE);
        createRequest.setAbout("Test ambulance");
        createRequest.setService_offers("Emergency,Oxygen");
        createRequest.setHospital_affiliation("Test Hospital");
        createRequest.setCoverage_areas("Dhaka");
        createRequest.setResponse_time(15);
        createRequest.setDoctors(1);
        createRequest.setNurses(2);
        createRequest.setParamedics(1);
        createRequest.setTeam_qualification("Qualified");
        createRequest.setStarting_fee(1000);

        // Set up review request
        reviewRequest = new AmbulanceReviewRequest();
        reviewRequest.setAmbulanceId(1L);
        reviewRequest.setRating(5);
        reviewRequest.setReview("Excellent service");
    }

    @Test
    void createAmbulanceRequest() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Success", null);
        // Use doReturn-when instead of when-thenReturn to avoid type inference issues
        doReturn(ResponseEntity.ok(successResponse))
                .when(ambulanceService).createAmbulanceRequest(any());

        // Act & Assert
        mockMvc.perform(post("/api/v1/ambulance/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Success"));

        verify(ambulanceService).createAmbulanceRequest(any());
    }

    @Test
    void createAmbulanceReviewRequest() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Review created", null);
        doReturn(ResponseEntity.ok(successResponse))
                .when(ambulanceService).createAmbulanceReviewRequest(any());

        // Act & Assert
        mockMvc.perform(post("/api/v1/ambulance/create-ambulance-review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Review created"));

        verify(ambulanceService).createAmbulanceReviewRequest(any());
    }

    @Test
    void getAmbulanceList() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Ambulances by area", null);
        doReturn(ResponseEntity.ok(successResponse))
                .when(ambulanceService).getAmbulanceList(any(), any());

        // Act & Assert
        mockMvc.perform(get("/api/v1/ambulance/list")
                        .param("type", "ICU")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Ambulances by area"));

        verify(ambulanceService).getAmbulanceList(eq(AmbulanceType.ICU), any());
    }

    @Test
    void getAmbulanceById() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Ambulance details", null);
        doReturn(ResponseEntity.ok(successResponse))
                .when(ambulanceService).getAmbulanceById(anyLong());

        // Act & Assert
        mockMvc.perform(get("/api/v1/ambulance/{ambulanceId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Ambulance details"));

        verify(ambulanceService).getAmbulanceById(eq(1L));
    }

    @Test
    void getAmbulancesByArea() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Ambulances by area", null);
        doReturn(ResponseEntity.ok(successResponse))
                .when(ambulanceService).getAmbulancesByArea(any(), anyString(), any());

        // Act & Assert
        mockMvc.perform(get("/api/v1/ambulance/by-area")
                        .param("type", "ICU")
                        .param("area", "Dhaka")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Ambulances by area"));

        verify(ambulanceService).getAmbulancesByArea(eq(AmbulanceType.ICU), eq("Dhaka"), any());
    }

    @Test
    void getAmbulanceReviews() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Ambulance reviews", null);
        doReturn(ResponseEntity.ok(successResponse))
                .when(ambulanceService).getAmbulanceReviews(anyLong(), any());

        // Act & Assert
        mockMvc.perform(get("/api/v1/ambulance/reviews/{ambulanceId}", 1L)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("direction", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Ambulance reviews"));

        verify(ambulanceService).getAmbulanceReviews(eq(1L), any());
    }

    @Test
    void updateAmbulanceStatus() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Status updated", null);
        doReturn(ResponseEntity.ok(successResponse))
                .when(ambulanceService).updateAmbulanceStatus(anyLong(), any());

        // Act & Assert
        mockMvc.perform(put("/api/v1/ambulance/{ambulanceId}/status", 1L)
                        .param("status", "UNAVAILABLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Status updated"));

        verify(ambulanceService).updateAmbulanceStatus(eq(1L), eq(AmbulanceStatus.UNAVAILABLE));
    }

    @Test
    void checkUserCanReview() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Can review", null);
        doReturn(ResponseEntity.ok(successResponse))
                .when(ambulanceService).checkUserCanReview(anyLong());

        // Act & Assert
        mockMvc.perform(get("/api/v1/ambulance/can-review/{ambulanceId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Can review"));

        verify(ambulanceService).checkUserCanReview(eq(1L));
    }
}