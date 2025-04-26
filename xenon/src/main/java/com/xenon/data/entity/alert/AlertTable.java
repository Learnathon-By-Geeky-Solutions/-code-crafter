package com.xenon.data.entity.alert;

import com.xenon.data.entity.healthAuthorization.HealthAuthorization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "alert_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "health_authorization_id")
    private HealthAuthorization healthAuthorization;

    @Column(length = 250, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(length = 1000)
    private String alertness;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double radius;

    @Column(name = "severity_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private AlertSeverity severityLevel = AlertSeverity.MEDIUM;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
        updatedAt = ZonedDateTime.now();
        if (startDate == null) {
            startDate = ZonedDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }

    public AlertTable(String title, String description, String alertness, HealthAuthorization healthAuthorization,
                      double latitude, double longitude, double radius) {
        this.title = title;
        this.description = description;
        this.alertness = alertness;
        this.healthAuthorization = healthAuthorization;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.startDate = ZonedDateTime.now();
    }

    public AlertTable(String title, String description, String alertness, HealthAuthorization healthAuthorization,
                      double latitude, double longitude, double radius, AlertSeverity severityLevel) {
        this(title, description, alertness, healthAuthorization, latitude, longitude, radius);
        this.severityLevel = severityLevel;
    }
}