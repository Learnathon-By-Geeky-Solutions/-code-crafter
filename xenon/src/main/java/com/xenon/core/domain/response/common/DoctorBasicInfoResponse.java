package com.xenon.core.domain.response.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorBasicInfoResponse {
    private Long id;
    private String name;
    private String specialist;
    private String doctorTitle;
    private Double rating;
    private Integer totalConsultations;
    private Integer consultationFee;
}