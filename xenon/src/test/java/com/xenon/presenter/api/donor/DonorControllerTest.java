package com.xenon.presenter.api.donor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.donor.BloodDonationInfoRequest;
import com.xenon.core.domain.request.donor.CreateDonorAccountRequest;
import com.xenon.core.domain.request.donor.UpdateDonorInterestRequest;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.domain.response.PageResponseRequest;
import com.xenon.core.service.donor.DonorService;
import com.xenon.data.entity.donor.BloodType;
import com.xenon.data.entity.donor.Interested;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DonorControllerTest {

    @Mock
    private DonorService donorService;

    @InjectMocks
    private DonorController donorController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CreateDonorAccountRequest createDonorAccountRequest;
    private BloodDonationInfoRequest bloodDonationInfoRequest;
    private UpdateDonorInterestRequest updateDonorInterestRequest;
    private final Long UPAZILA_ID = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(donorController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // For handling dates
        
        // Initialize create donor account request
        createDonorAccountRequest = new CreateDonorAccountRequest();
        createDonorAccountRequest.setBloodType(BloodType.A_POS);
        createDonorAccountRequest.setAge(30);
        createDonorAccountRequest.setWeight(75);
        createDonorAccountRequest.setInterested(Interested.YES);
        
        // Initialize blood donation info request
        bloodDonationInfoRequest = new BloodDonationInfoRequest();
        bloodDonationInfoRequest.setPatientName("Jane Doe");
        bloodDonationInfoRequest.setQuantity(1);
        bloodDonationInfoRequest.setHospitalName("City Hospital");
        bloodDonationInfoRequest.setLastDonation(LocalDate.now());
        
        // Initialize update donor interest request
        updateDonorInterestRequest = new UpdateDonorInterestRequest();
        updateDonorInterestRequest.setInterested(Interested.YES);
    }

    @Test
    void createDonorAccount_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Donor created successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(donorService).createDonorAccountRequest(any(CreateDonorAccountRequest.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/donor/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDonorAccountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Donor created successfully"));

        // Verify
        verify(donorService).createDonorAccountRequest(any(CreateDonorAccountRequest.class));
    }

    @Test
    void bloodGiven_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Success", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(donorService).bloodGivenInfoRequest(any(BloodDonationInfoRequest.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/donor/blood-given")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bloodDonationInfoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Success"));

        // Verify
        verify(donorService).bloodGivenInfoRequest(any(BloodDonationInfoRequest.class));
    }

    @Test
    void getDonationHistory_ShouldReturnDonationHistory() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Donation History Fetched Successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(donorService).getDonationHistory();

        // Act & Assert
        mockMvc.perform(get("/api/v1/donor/donation-history")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Donation History Fetched Successfully"));

        // Verify
        verify(donorService).getDonationHistory();
    }

    @Test
    void updateDonorInterest_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Donor interest updated successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(donorService).updateDonorInterest(any(UpdateDonorInterestRequest.class));

        // Act & Assert
        mockMvc.perform(put("/api/v1/donor/update-interest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDonorInterestRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Donor interest updated successfully"));

        // Verify
        verify(donorService).updateDonorInterest(any(UpdateDonorInterestRequest.class));
    }

    @Test
    void getDonorProfile_ShouldReturnDonorProfile() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Donor profile retrieved successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(donorService).getDonorProfile();

        // Act & Assert
        mockMvc.perform(get("/api/v1/donor/profile")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Donor profile retrieved successfully"));

        // Verify
        verify(donorService).getDonorProfile();
    }

    @Test
    void getAvailableDonors_WithAllParameters_ShouldReturnFilteredDonors() throws Exception {
        // Arrange
        BloodType bloodType = BloodType.A_POS;
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Available donors retrieved successfully", new PageResponseRequest<>(null, 0, 10, 5, 1));
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(donorService).getAvailableDonors(any(BloodType.class), anyLong(), any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/donor/available")
                .param("bloodType", bloodType.name())
                .param("upazilaId", UPAZILA_ID.toString())
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("direction", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Available donors retrieved successfully"));

        // Verify
        verify(donorService).getAvailableDonors(eq(bloodType), eq(UPAZILA_ID), any(Pageable.class));
    }

    @Test
    void getAvailableDonors_WithOnlyBloodType_ShouldReturnFilteredDonors() throws Exception {
        // Arrange
        BloodType bloodType = BloodType.A_POS;
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Available donors retrieved successfully", new PageResponseRequest<>(null, 0, 10, 5, 1));
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(donorService).getAvailableDonors(any(BloodType.class), isNull(), any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/donor/available")
                .param("bloodType", bloodType.name())
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("direction", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Available donors retrieved successfully"));

        // Verify
        verify(donorService).getAvailableDonors(eq(bloodType), isNull(), any(Pageable.class));
    }

    @Test
    void getAvailableDonors_WithOnlyUpazilaId_ShouldReturnFilteredDonors() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Available donors retrieved successfully", new PageResponseRequest<>(null, 0, 10, 5, 1));
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(donorService).getAvailableDonors(isNull(), anyLong(), any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/donor/available")
                .param("upazilaId", UPAZILA_ID.toString())
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("direction", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Available donors retrieved successfully"));

        // Verify
        verify(donorService).getAvailableDonors(isNull(), eq(UPAZILA_ID), any(Pageable.class));
    }

    @Test
    void getAvailableDonors_WithNoFilters_ShouldReturnAllAvailableDonors() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Available donors retrieved successfully", new PageResponseRequest<>(null, 0, 10, 20, 2));
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(donorService).getAvailableDonors(isNull(), isNull(), any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/donor/available")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("direction", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Available donors retrieved successfully"));

        // Verify
        verify(donorService).getAvailableDonors(isNull(), isNull(), any(Pageable.class));
    }
}