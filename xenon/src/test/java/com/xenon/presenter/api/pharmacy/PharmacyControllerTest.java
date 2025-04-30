package com.xenon.presenter.api.pharmacy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.pharmacy.CreatePharmacyAccountRequest;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.service.pharmacy.PharmacyService;
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

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PharmacyControllerTest {

    @Mock
    private PharmacyService pharmacyService;

    @InjectMocks
    private PharmacyController pharmacyController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CreatePharmacyAccountRequest createPharmacyAccountRequest;
    private final Long PHARMACY_ID = 1L;
    private final Long UPAZILA_ID = 1L;
    private final String SEARCH_NAME = "Medi";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pharmacyController).build();
        objectMapper = new ObjectMapper();
        
        // Initialize pharmacy account request
        createPharmacyAccountRequest = new CreatePharmacyAccountRequest();
        createPharmacyAccountRequest.setTradeLicenseNumber("TL-12345");
    }

    @Test
    void createPharmacy_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Pharmacy created successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(pharmacyService).createPharmacyRequest(any(CreatePharmacyAccountRequest.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/pharmacy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPharmacyAccountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Pharmacy created successfully"));

        // Verify
        verify(pharmacyService).createPharmacyRequest(any(CreatePharmacyAccountRequest.class));
    }

    @Test
    void getPharmacyById_ShouldReturnPharmacyDetails() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Pharmacy retrieved successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(pharmacyService).getPharmacyById(anyLong());

        // Act & Assert
        mockMvc.perform(get("/api/v1/pharmacy/{id}", PHARMACY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Pharmacy retrieved successfully"));

        // Verify
        verify(pharmacyService).getPharmacyById(eq(PHARMACY_ID));
    }

    @Test
    void getAllPharmacies_ShouldReturnPaginatedPharmacies() throws Exception {
        // Arrange
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("content", null);
        responseMap.put("currentPage", 0);
        responseMap.put("totalItems", 20L);
        responseMap.put("totalPages", 2);
        responseMap.put("size", 10);
        
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Pharmacies retrieved successfully", responseMap);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(pharmacyService).getAllPharmacies(any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/pharmacy")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("sortDir", "DESC")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Pharmacies retrieved successfully"))
                .andExpect(jsonPath("$.data.currentPage").value(0))
                .andExpect(jsonPath("$.data.totalItems").value(20))
                .andExpect(jsonPath("$.data.totalPages").value(2))
                .andExpect(jsonPath("$.data.size").value(10));

        // Verify
        verify(pharmacyService).getAllPharmacies(any(Pageable.class));
    }

    @Test
    void searchPharmacies_ShouldReturnMatchingPharmacies() throws Exception {
        // Arrange
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("content", null);
        responseMap.put("currentPage", 0);
        responseMap.put("totalItems", 5L);
        responseMap.put("totalPages", 1);
        responseMap.put("size", 10);
        
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Pharmacies retrieved successfully", responseMap);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(pharmacyService).searchPharmacies(anyString(), any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/pharmacy/search")
                .param("name", SEARCH_NAME)
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("sortDir", "ASC")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Pharmacies retrieved successfully"))
                .andExpect(jsonPath("$.data.currentPage").value(0))
                .andExpect(jsonPath("$.data.totalItems").value(5))
                .andExpect(jsonPath("$.data.totalPages").value(1))
                .andExpect(jsonPath("$.data.size").value(10));

        // Verify
        verify(pharmacyService).searchPharmacies(eq(SEARCH_NAME), any(Pageable.class));
    }

    @Test
    void getPharmaciesByUpazilaId_ShouldReturnPharmaciesInUpazila() throws Exception {
        // Arrange
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("content", null);
        responseMap.put("currentPage", 0);
        responseMap.put("totalItems", 3L);
        responseMap.put("totalPages", 1);
        responseMap.put("size", 10);
        
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Pharmacies by upazila retrieved successfully", responseMap);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(pharmacyService).getPharmaciesByUpazilaId(anyLong(), any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/pharmacy/by-upazila/{upazilaId}", UPAZILA_ID)
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("sortDir", "ASC")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Pharmacies by upazila retrieved successfully"))
                .andExpect(jsonPath("$.data.currentPage").value(0))
                .andExpect(jsonPath("$.data.totalItems").value(3))
                .andExpect(jsonPath("$.data.totalPages").value(1))
                .andExpect(jsonPath("$.data.size").value(10));

        // Verify
        verify(pharmacyService).getPharmaciesByUpazilaId(eq(UPAZILA_ID), any(Pageable.class));
    }
}