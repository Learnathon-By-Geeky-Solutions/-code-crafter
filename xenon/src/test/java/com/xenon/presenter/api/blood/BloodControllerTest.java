package com.xenon.presenter.api.blood;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.blood.CreateBloodCommentRequest;
import com.xenon.core.domain.request.blood.CreateBloodRequestPost;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.domain.response.PageResponseRequest;
import com.xenon.core.service.blood.BloodCommentService;
import com.xenon.core.service.blood.BloodRequestPostService;
import com.xenon.data.entity.donor.BloodType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BloodControllerTest {

    @Mock
    private BloodRequestPostService bloodRequestPostService;

    @Mock
    private BloodCommentService bloodCommentService;

    @InjectMocks
    private BloodController bloodController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CreateBloodRequestPost bloodRequestPost;
    private CreateBloodCommentRequest bloodCommentRequest;
    private final Long POST_ID = 1L;
    private final Long UPAZILA_ID = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bloodController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // For handling dates
        
        // Initialize blood request post
        bloodRequestPost = new CreateBloodRequestPost();
        bloodRequestPost.setPatientName("John Doe");
        bloodRequestPost.setBloodType(BloodType.A_POS);
        bloodRequestPost.setQuantity(2);
        bloodRequestPost.setHospitalName("City Hospital");
        bloodRequestPost.setContactNumber("01700000000");
        bloodRequestPost.setDescription("Urgent need for surgery");
        bloodRequestPost.setDate(LocalDate.now().plusDays(1));
        bloodRequestPost.setUpazilaId(UPAZILA_ID);
        
        // Initialize blood comment request
        bloodCommentRequest = new CreateBloodCommentRequest();
        bloodCommentRequest.setPostId(POST_ID);
        bloodCommentRequest.setComment("I can donate blood tomorrow");
    }

    @Test
    void createBloodRequest_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blood request post created successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(bloodRequestPostService).createBloodRequestPost(any(CreateBloodRequestPost.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/blood/create-request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bloodRequestPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blood request post created successfully"));

        // Verify
        verify(bloodRequestPostService).createBloodRequestPost(any(CreateBloodRequestPost.class));
    }

    @Test
    void createBloodComment_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blood comment created successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(bloodCommentService).createBloodCommentRequest(any(CreateBloodCommentRequest.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/blood/create-comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bloodCommentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blood comment created successfully"));

        // Verify
        verify(bloodCommentService).createBloodCommentRequest(any(CreateBloodCommentRequest.class));
    }

    @Test
    void getBloodDashboard_ShouldReturnDashboardData() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blood dashboard retrieved successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(bloodRequestPostService).getBloodDashboard();

        // Act & Assert
        mockMvc.perform(get("/api/v1/blood/dashboard-blood")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blood dashboard retrieved successfully"));

        // Verify
        verify(bloodRequestPostService).getBloodDashboard();
    }

    @Test
    void getBloodRequestPostPage_ShouldReturnPaginatedPosts() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blood request posts retrieved successfully", new PageResponseRequest<>(null, 0, 10, 20, 2));
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(bloodRequestPostService).getBloodRequestPostPage(any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/blood/blood-request-post-page")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "date")
                .param("direction", "desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blood request posts retrieved successfully"));

        // Verify
        verify(bloodRequestPostService).getBloodRequestPostPage(any(Pageable.class));
    }

    @Test
    void getBloodPostPage_ShouldReturnPaginatedPosts() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blood posts retrieved successfully", new PageResponseRequest<>(null, 0, 10, 20, 2));
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(bloodRequestPostService).getBloodPostPage(any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/blood/blood-post-page")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "date")
                .param("direction", "desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blood posts retrieved successfully"));

        // Verify
        verify(bloodRequestPostService).getBloodPostPage(any(Pageable.class));
    }

    @Test
    void getBloodRequestsByType_ShouldReturnFilteredPosts() throws Exception {
        // Arrange
        BloodType bloodType = BloodType.A_POS;
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blood requests by type retrieved successfully", new PageResponseRequest<>(null, 0, 10, 5, 1));
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(bloodRequestPostService).getBloodRequestsByType(any(BloodType.class), any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/blood/requests/by-type")
                .param("bloodType", bloodType.name())
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "date")
                .param("direction", "desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blood requests by type retrieved successfully"));

        // Verify
        verify(bloodRequestPostService).getBloodRequestsByType(eq(bloodType), any(Pageable.class));
    }

    @Test
    void getBloodRequestsByLocation_ShouldReturnFilteredPosts() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blood requests by location retrieved successfully", new PageResponseRequest<>(null, 0, 10, 5, 1));
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(bloodRequestPostService).getBloodRequestsByLocation(anyLong(), any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/blood/requests/by-location")
                .param("upazilaId", UPAZILA_ID.toString())
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "date")
                .param("direction", "desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blood requests by location retrieved successfully"));

        // Verify
        verify(bloodRequestPostService).getBloodRequestsByLocation(eq(UPAZILA_ID), any(Pageable.class));
    }

    @Test
    void getBloodRequestsByTypeAndLocation_ShouldReturnFilteredPosts() throws Exception {
        // Arrange
        BloodType bloodType = BloodType.A_POS;
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blood requests by type and location retrieved successfully", new PageResponseRequest<>(null, 0, 10, 3, 1));
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(bloodRequestPostService).getBloodRequestsByTypeAndLocation(any(BloodType.class), anyLong(), any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/blood/requests/filter")
                .param("bloodType", bloodType.name())
                .param("upazilaId", UPAZILA_ID.toString())
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "date")
                .param("direction", "desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blood requests by type and location retrieved successfully"));

        // Verify
        verify(bloodRequestPostService).getBloodRequestsByTypeAndLocation(eq(bloodType), eq(UPAZILA_ID), any(Pageable.class));
    }

    @Test
    void getBloodRequestDetails_ShouldReturnRequestDetails() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blood request details retrieved successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(bloodRequestPostService).getBloodRequestDetails(anyLong());

        // Act & Assert
        mockMvc.perform(get("/api/v1/blood/requests/{requestId}", POST_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blood request details retrieved successfully"));

        // Verify
        verify(bloodRequestPostService).getBloodRequestDetails(eq(POST_ID));
    }
}