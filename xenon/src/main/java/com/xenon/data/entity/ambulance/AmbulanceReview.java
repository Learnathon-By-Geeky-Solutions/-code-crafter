package com.xenon.data.entity.ambulance;

import com.xenon.core.domain.response.ambulance.AmbulanceReviewResponse;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ambulance_review")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmbulanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AMBULANCE_ID")
    private Ambulance ambulance;

    @Column
    private Integer rating;

    @Column
    private String review;

    @Column(name = "review_date")
    private LocalDate date;

    public AmbulanceReview(User user, Ambulance ambulance, Integer rating, String review) {
        this.user = user;
        this.ambulance = ambulance;
        this.rating = rating;
        this.review = review;
        this.date = LocalDate.now();
    }

    public AmbulanceReviewResponse toResponse() {
        return new AmbulanceReviewResponse(
                id,
                user.toResponse(),
                rating,
                review,
                date
        );
    }
}
