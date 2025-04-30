package com.xenon.core.domain.response.ambulance;

import com.xenon.core.domain.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmbulanceReviewResponse {
    private Long id;
    private UserResponse user;
    private Integer rating;
    private String review;
    private LocalDate date;
}
