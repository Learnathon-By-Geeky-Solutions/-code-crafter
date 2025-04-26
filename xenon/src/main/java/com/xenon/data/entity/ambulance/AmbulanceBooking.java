package com.xenon.data.entity.ambulance;

import com.xenon.core.domain.response.ambulance.AmbulanceBookingResponse;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ambulance_booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmbulanceBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AMBULANCE_ID")
    private Ambulance ambulance;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AmbulanceBookingStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public AmbulanceBooking(User user, Ambulance ambulance) {
        this.user = user;
        this.ambulance = ambulance;
        this.bookingTime = LocalDateTime.now();
        this.status = AmbulanceBookingStatus.PENDING;
    }

    public AmbulanceBookingResponse toResponse() {
        return new AmbulanceBookingResponse(
                id,
                user.toResponse(),
                ambulance.toResponse(),
                bookingTime,
                status
        );
    }
}