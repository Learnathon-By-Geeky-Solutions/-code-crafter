package com.xenon.data.entity.hospital;

import com.xenon.data.entity.doctor.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "offline_doctor_affiliation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineDoctorAffiliation {

    @EmbeddedId
    private DoctorAffiliationId id;

    @MapsId("doctorId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @MapsId("hospitalBranchId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hospital_branch_id")
    private HospitalBranch hospitalBranch;

    private Integer fee;

    public OfflineDoctorAffiliation(Doctor doctor, HospitalBranch hospitalBranch, Integer fee) {
        this.doctor = doctor;
        this.hospitalBranch = hospitalBranch;
        this.fee = fee;
        this.id = new DoctorAffiliationId(doctor.getId(), hospitalBranch.getId());
    }
}
