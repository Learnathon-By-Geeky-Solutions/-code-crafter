package com.xenon.core.domain.response.doctor;

import com.xenon.core.domain.response.user.UserResponse;
import com.xenon.data.entity.doctor.DoctorTitle;
import com.xenon.data.entity.doctor.SpecialistCategory;
import com.xenon.data.entity.location.Upazila;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoctorProfileResponse {
    private Long id;
    private String name;
    private SpecialistCategory specialist;
    private DoctorTitle doctorTitle;
    private String about;
    private List<String> areasOfExpertise;
    private List<String> patientCarePhilosophy;
    private List<String> education;
    private String experience;
    private List<String> awards;
    private List<String> publications;
    private Upazila upazila;
    private String address;
    private String phone;
    private String email;
}
