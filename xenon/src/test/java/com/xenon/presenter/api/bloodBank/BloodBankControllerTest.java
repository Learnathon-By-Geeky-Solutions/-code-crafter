package com.xenon.presenter.api.bloodBank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.bloodBank.CreateBloodBankAccountRequest;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.domain.response.PageResponseRequest;
import com.xenon.core.service.bloodBank.BloodBankService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BloodBankControllerTest {

    @Mock
    private BloodBankService bloodBankService;

    @InjectMocks
    private BloodBankController bloodBankController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CreateBloodBankAccountRequest createBloodBankAccountRequest;
    private final Long BLOOD_BANK_ID = 1L;
    private final Long UPAZILA_ID = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bloodBankController).build();
        objectMapper = new ObjectMapper();
        
        // Initialize blood bank account request
        createBloodBankAccountRequest = new CreateBloodBankAccountRequest();
        createBloodBankAccountRequest.setRegistration_number("REG-12345");
    }

    @Test
    void createBloodBank_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blood Bank created successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(bloodBankService).createBloodBankRequest(any(CreateBloodBankAccountRequest.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/blood-bank/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBloodBankAccountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blood Bank created successfully"));

        // Verify
        verify(bloodBankService).createBloodBankRequest(any(CreateBloodBankAccountRequest.class));
    }

    @Test
    void getAllBloodBanks_ShouldReturnPaginatedBloodBanks() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blood banks retrieved successfully", new PageResponseRequest<>(null, 0, 10, 20, 2));
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(bloodBankService).getAllBloodBanks(any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/blood-bank")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("direction", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blood banks retrieved successfully"));

        // Verify
        verify(bloodBankService).getAllBloodBanks(any(Pageable.class));
    }

    @Test
    void getBloodBanksByLocation_ShouldReturnFilteredBloodBanks() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blood banks by location retrieved successfully", new PageResponseRequest<>(null, 0, 10, 5, 1));
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(bloodBankService).getBloodBanksByLocation(anyLong(), any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/blood-bank/by-location")
                .param("upazilaId", UPAZILA_ID.toString())
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("direction", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blood banks by location retrieved successfully"));

        // Verify
        verify(bloodBankService).getBloodBanksByLocation(eq(UPAZILA_ID), any(Pageable.class));
    }

    @Test
    void getBloodBankDetails_ShouldReturnBloodBankDetails() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blood bank details retrieved successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(bloodBankService).getBloodBankDetails(anyLong());

        // Act & Assert
        mockMvc.perform(get("/api/v1/blood-bank/{bloodBankId}", BLOOD_BANK_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blood bank details retrieved successfully"));

        // Verify
        verify(bloodBankService).getBloodBankDetails(eq(BLOOD_BANK_ID));
    }
}