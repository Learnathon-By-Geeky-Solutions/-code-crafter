package com.xenon.core.domain.request.hospital;

import com.xenon.data.entity.hospital.Hospital;
import com.xenon.data.entity.hospital.HospitalBranch;
import com.xenon.data.entity.location.Upazila;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHospitalBranchAccountRequest {

    private Long hospital;
    private Long upazila;
    private String branchName;
    private String address;
    private String phone;
    private String email;
    private Integer doctor;
    private Integer bed;
    private LocalDate establish_date;

    public HospitalBranch toEntity(Hospital hospital, Upazila upazila) {
        return new HospitalBranch(hospital, upazila, branchName, address, phone, email, doctor, bed, establish_date);
    }

}
