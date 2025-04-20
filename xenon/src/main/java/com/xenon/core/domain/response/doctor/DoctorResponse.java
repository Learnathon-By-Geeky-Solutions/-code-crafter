package com.xenon.core.domain.response.doctor;

import com.xenon.core.domain.response.user.UserResponse;
import com.xenon.data.entity.doctor.DoctorTitle;
import com.xenon.data.entity.doctor.DoctorType;
import com.xenon.data.entity.doctor.SpecialistCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoctorResponse {
    private Long id;
    private UserResponse user;
    private DoctorTitle doctorTitle;
    private DoctorType doctorType;
    private SpecialistCategory specialistCategory;
    private LocalDate dateOfBirth;
    private String nid;
    private String passport;
    private String registrationNo;
    private Integer experience;
    private String hospital;
    private String about;
    private String areaOfExpertise;
    private String patientCarePolicy;
    private String education;
    private String experienceInfo;
    private String awards;
    private String publications;
}
