package com.xenon.data.entity.hospital;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAffiliationId implements Serializable {

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "hospital_branch_id")
    private Long hospitalBranchId;
}
