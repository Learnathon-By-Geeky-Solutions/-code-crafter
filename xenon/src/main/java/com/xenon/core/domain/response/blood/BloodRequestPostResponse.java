package com.xenon.core.domain.response.blood;

import com.xenon.data.entity.donor.BloodType;
import com.xenon.data.entity.location.Upazila;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodRequestPostResponse {
    private Long id;
    private Upazila upazila;
    private String patientName;
    private BloodType bloodType;
    private Integer quantity;
    private String hospitalName;
    private LocalDate date;
    private String contactNumber;
    private String description;
    private List<BloodRequestPostCommentResponse> bloodRequestPostCommentResponses;
}
