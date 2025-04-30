package com.xenon.presenter.api.doctor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.doctor.DoctorProfileRequest;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.service.doctor.DoctorService;
import com.xenon.data.entity.doctor.DoctorTitle;
import com.xenon.data.entity.doctor.SpecialistCategory;
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

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private DoctorProfileRequest doctorProfileRequest;
    private final Long DOCTOR_ID = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // For handling dates
        
        // Initialize doctor profile request
        doctorProfileRequest = new DoctorProfileRequest();
        doctorProfileRequest.setDoctorTitle(DoctorTitle.Dr);
        doctorProfileRequest.setSpecialistCategory(SpecialistCategory.CARDIOLOGY);
        doctorProfileRequest.setDateOfBirth(LocalDate.of(1980, 1, 15));
        doctorProfileRequest.setNid("1234567890");
        doctorProfileRequest.setPassport("AB1234567");
        doctorProfileRequest.setRegistrationNo("REG12345");
        doctorProfileRequest.setExperience(10);
        doctorProfileRequest.setHospital("City Medical Center");
        doctorProfileRequest.setAbout("Experienced cardiologist with over 10 years of practice");
        doctorProfileRequest.setAreaOfExpertise("Cardiac surgery, Angioplasty, Heart disease treatment");
        doctorProfileRequest.setPatientCarePolicy("Patient-centered care with focus on preventive medicine");
        doctorProfileRequest.setEducation("MBBS from Medical University, MD in Cardiology");
        doctorProfileRequest.setExperienceInfo("Worked at various hospitals, including 5 years at City Hospital");
        doctorProfileRequest.setAwards("Best Cardiologist Award 2020");
        doctorProfileRequest.setPublications("Published research on cardiac treatments in medical journals");
    }

    @Test
    void createDoctorProfile_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Doctor profile created successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(doctorService).createDoctorProfileRequest(any(DoctorProfileRequest.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/doctors/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctorProfileRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Doctor profile created successfully"));

        // Verify
        verify(doctorService).createDoctorProfileRequest(any(DoctorProfileRequest.class));
    }

    @Test
    void getDoctorProfile_ShouldReturnCurrentDoctorProfile() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Doctor profile retrieved successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(doctorService).getDoctorProfile();

        // Act & Assert
        mockMvc.perform(get("/api/v1/doctors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Doctor profile retrieved successfully"));

        // Verify
        verify(doctorService).getDoctorProfile();
    }

    @Test
    void getDoctorProfileById_ShouldReturnDoctorProfile() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Doctor profile retrieved successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(doctorService).getDoctorProfile(anyLong());

        // Act & Assert
        mockMvc.perform(get("/api/v1/doctors/{doctorId}", DOCTOR_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Doctor profile retrieved successfully"));

        // Verify
        verify(doctorService).getDoctorProfile(eq(DOCTOR_ID));
    }

    @Test
    void updateDoctorProfile_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Doctor profile updated successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(doctorService).updateDoctorProfile(anyLong(), any(DoctorProfileRequest.class));

        // Update the profile request
        doctorProfileRequest.setAbout("Updated about information for the doctor");
        doctorProfileRequest.setExperience(15);

        // Act & Assert
        mockMvc.perform(put("/api/v1/doctors/{doctorId}", DOCTOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctorProfileRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Doctor profile updated successfully"));

        // Verify
        verify(doctorService).updateDoctorProfile(eq(DOCTOR_ID), any(DoctorProfileRequest.class));
    }
}