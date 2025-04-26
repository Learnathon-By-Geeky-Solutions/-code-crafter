package com.xenon.data.entity.alert;

import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "user_location")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;
    
    @Column(name = "location_allowed", nullable = false)
    private boolean locationAllowed = false;
    
    @Column(name = "last_updated", nullable = false)
    private ZonedDateTime lastUpdated;
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = ZonedDateTime.now();
    }
    
    public UserLocation(User user, Double latitude, Double longitude, boolean locationAllowed) {
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationAllowed = locationAllowed;
        this.lastUpdated = ZonedDateTime.now();
    }
}