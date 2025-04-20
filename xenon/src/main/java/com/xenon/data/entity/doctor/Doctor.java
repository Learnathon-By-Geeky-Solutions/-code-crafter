package com.xenon.data.entity.doctor;

import com.xenon.core.domain.response.doctor.DoctorResponse;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "doctor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DoctorTitle doctorTitle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DoctorType doctorType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private SpecialistCategory specialistCategory;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(length = 20, unique = true)
    private String nid;

    @Column(length = 20, unique = true)
    private String passport;

    @Column(length = 20, unique = true, nullable = false)
    private String registrationNo;

    @Column(length = 60, nullable = false)
    private Integer experience;

    @Column(length = 100, nullable = false)
    private String hospital;

    @Column(length = 1000, nullable = false)
    private String about;

    @Column(length = 1000, nullable = false)
    private String areaOfExpertise;

    @Column(length = 1000, nullable = false)
    private String patientCarePolicy;

    @Column(length = 1000, nullable = false)
    private String education;

    @Column(length = 1000, nullable = false)
    private String experienceInfo;

    @Column(length = 1000)
    private String awards;

    @Column(length = 1000)
    private String publications;

    public Doctor(DoctorTitle doctorTitle, DoctorType doctorType, SpecialistCategory specialistCategory, LocalDate dateOfBirth, String nid, String passport, String registrationNo, Integer experience, String hospital, String about, String areaOfExpertise, String patientCarePolicy, String education, String experienceInfo, String awards, String publications, User user) {
        this.doctorTitle = doctorTitle;
        this.doctorType = doctorType;
        this.specialistCategory = specialistCategory;
        this.dateOfBirth = dateOfBirth;
        this.nid = nid;
        this.passport = passport;
        this.registrationNo = registrationNo;
        this.experience = experience;
        this.hospital = hospital;
        this.about = about;
        this.areaOfExpertise = areaOfExpertise;
        this.patientCarePolicy = patientCarePolicy;
        this.education = education;
        this.experienceInfo = experienceInfo;
        this.awards = awards;
        this.publications = publications;
        this.user = user;
    }

    public DoctorResponse toResponse() {
        return new DoctorResponse(id, user.toResponse(), doctorTitle, doctorType, specialistCategory, dateOfBirth, nid, passport, registrationNo, experience, hospital, about, areaOfExpertise, patientCarePolicy, education, experienceInfo, awards, publications);
    }
}
