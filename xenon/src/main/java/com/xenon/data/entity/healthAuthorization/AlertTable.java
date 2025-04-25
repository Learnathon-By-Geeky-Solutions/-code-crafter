package com.xenon.data.entity.healthAuthorization;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(length = 250)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String alertness;

    @Column(nullable = false)
    private double latitude;


    @Column(nullable = false)
    private double longitude;

    @Column
    private double radius;


    public AlertTable(String title, String description, String alertness, HealthAuthorization healthAuthorization, double latitude, double longitude, double radius) {
        this.title = title;
        this.description = description;
        this.alertness = alertness;
        this.healthAuthorization = healthAuthorization;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }
}
