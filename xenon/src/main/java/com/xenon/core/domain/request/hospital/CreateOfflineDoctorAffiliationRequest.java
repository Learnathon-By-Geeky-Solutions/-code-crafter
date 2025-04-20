package com.xenon.core.domain.request.hospital;

import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.hospital.HospitalBranch;
import com.xenon.data.entity.hospital.OfflineDoctorAffiliation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOfflineDoctorAffiliationRequest {

    private Long doctor;
    private Long hospitalBranch;
    private Integer fee;

    public OfflineDoctorAffiliation toEntity(Doctor doctor, HospitalBranch hospitalBranch) {
        return new OfflineDoctorAffiliation(doctor, hospitalBranch, fee);
    }
}
