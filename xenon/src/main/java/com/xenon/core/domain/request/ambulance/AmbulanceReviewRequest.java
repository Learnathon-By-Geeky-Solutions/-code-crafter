package com.xenon.core.domain.request.ambulance;

import com.xenon.data.entity.ambulance.Ambulance;
import com.xenon.data.entity.ambulance.AmbulanceReview;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmbulanceReviewRequest {

    private Long ambulanceId;
    private Integer rating;
    private String review;

    public AmbulanceReview toEntity(User user, Ambulance ambulance) {
        return new AmbulanceReview(user, ambulance, rating, review);
    }
}
