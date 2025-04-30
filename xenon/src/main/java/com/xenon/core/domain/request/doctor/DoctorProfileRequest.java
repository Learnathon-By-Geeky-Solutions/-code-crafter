package com.xenon.core.domain.request.doctor;

import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.doctor.DoctorTitle;
import com.xenon.data.entity.doctor.SpecialistCategory;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorProfileRequest {

    private DoctorTitle doctorTitle;
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

    public Doctor toEntity(User user) {
        return new Doctor(doctorTitle, specialistCategory, dateOfBirth, nid, passport, registrationNo, experience, hospital, about, areaOfExpertise, patientCarePolicy, education, experienceInfo, awards, publications, user);
    }
}
