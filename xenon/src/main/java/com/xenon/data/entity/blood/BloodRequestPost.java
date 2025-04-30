package com.xenon.data.entity.blood;

import com.xenon.core.domain.response.blood.BloodRequestPostCommentResponse;
import com.xenon.core.domain.response.blood.BloodRequestPostResponse;
import com.xenon.data.entity.donor.BloodType;
import com.xenon.data.entity.location.Upazila;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "blood_request_post")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodRequestPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UPAZILA_ID")
    private Upazila upazila;

    @Column(nullable = false, length = 100)
    private String patientName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private BloodType bloodType;

    @Column(nullable = false, length = 10)
    private Integer quantity;

    @Column(nullable = false, length = 100)
    private String hospitalName;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, length = 100)
    private String contactNumber;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
        updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }

    public BloodRequestPost(User user, Upazila upazila, String patientName, BloodType bloodType, Integer quantity, String hospitalName, String description, LocalDate date, String contactNumber) {

        this.user = user;
        this.upazila = upazila;
        this.patientName = patientName;
        this.bloodType = bloodType;
        this.quantity = quantity;
        this.hospitalName = hospitalName;
        this.description = description;
        this.date = date;
        this.contactNumber = contactNumber;
    }

    public BloodRequestPostResponse toResponse(List<BloodRequestPostCommentResponse> commentResponses) {
        return new BloodRequestPostResponse(id, upazila, patientName, bloodType, quantity, hospitalName, date, contactNumber, description, commentResponses);
    }
}
