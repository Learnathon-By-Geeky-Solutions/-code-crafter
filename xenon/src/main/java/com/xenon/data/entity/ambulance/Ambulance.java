package com.xenon.data.entity.ambulance;

import com.xenon.core.domain.response.ambulance.AmbulanceResponse;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ambulance")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ambulance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private AmbulanceType ambulanceType;

    @Column(length = 30, nullable = false, unique = true)
    private String ambulanceNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private AmbulanceStatus ambulanceStatus;

    @Column(length = 1000, nullable = false)
    private String about;

    @Column(length = 500, nullable = false)
    private String service_offers;

    @Column(length = 500)
    private String hospital_affiliation;

    @Column(length = 200, nullable = false)
    private String coverage_areas;

    @Column(nullable = false)
    private Integer response_time;

    @Column
    private Integer doctors;

    @Column
    private Integer nurses;

    @Column
    private Integer paramedics;

    @Column(nullable = false, length = 700)
    private String team_qualification;

    @Column(nullable = false)
    private Integer starting_fee;


    public Ambulance(AmbulanceType ambulanceType, String ambulanceNumber, AmbulanceStatus ambulanceStatus, User user, String about, String serviceOffers, String hospitalAffiliation, String coverageAreas, Integer responseTime, Integer doctors, Integer nurses, Integer paramedics, String teamQualification, Integer startingFee) {
        this.ambulanceType = ambulanceType;
        this.ambulanceNumber = ambulanceNumber;
        this.ambulanceStatus = ambulanceStatus;
        this.user = user;
        this.about = about;
        this.service_offers = serviceOffers;
        this.hospital_affiliation = hospitalAffiliation;
        this.coverage_areas = coverageAreas;
        this.response_time = responseTime;
        this.doctors = doctors;
        this.nurses = nurses;
        this.paramedics = paramedics;
        this.team_qualification = teamQualification;
        this.starting_fee = startingFee;
    }

    public AmbulanceResponse toResponse() {
        return new AmbulanceResponse(id, user.toResponse(), ambulanceType, ambulanceNumber, ambulanceStatus, about, service_offers, hospital_affiliation, coverage_areas, response_time, doctors, nurses, paramedics, team_qualification, starting_fee);
    }
}
