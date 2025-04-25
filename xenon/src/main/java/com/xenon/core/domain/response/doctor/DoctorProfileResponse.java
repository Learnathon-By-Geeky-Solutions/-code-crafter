package com.xenon.core.domain.response.doctor;

import com.xenon.core.domain.response.user.UserResponse;
import com.xenon.data.entity.doctor.DoctorTitle;
import com.xenon.data.entity.doctor.SpecialistCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoctorProfileResponse {
    private Long id;
    private String name;
    private String specialist;
    private String doctorTitle;
    private Double rating;
    private Integer totalConsultations;
    private Integer consultationFee;
    private String about;
    private List<String> areasOfExpertise;
    private List<String> patientCarePhilosophy;
    private List<String> education;
    private String experience;
    private List<String> awards;
    private List<String> publications;
    private String address;
    private String phone;
    private String email;
}
