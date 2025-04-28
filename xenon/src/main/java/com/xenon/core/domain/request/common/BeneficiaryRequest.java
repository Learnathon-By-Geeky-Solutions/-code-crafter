package com.xenon.core.domain.request.common;

import com.xenon.data.entity.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaryRequest {
    private Boolean isBeneficiary;
    private String beneficiaryName;
    private String beneficiaryPhone;
    private String beneficiaryAddress;
    private Gender beneficiaryGender;
    private Integer beneficiaryAge;
    private String medicalHistoryFile;
}