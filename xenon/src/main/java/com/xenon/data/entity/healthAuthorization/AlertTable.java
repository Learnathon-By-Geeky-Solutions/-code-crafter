package com.xenon.data.entity.healthAuthorization;

import com.xenon.data.entity.location.Upazila;
import com.xenon.data.entity.user.User;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "upazila_id", unique = true, nullable = false)
    private Upazila upazila;

    @Column(length = 250)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String alertness;


    public AlertTable(String title, String description, String alertness, Upazila upazila, HealthAuthorization healthAuthorization) {
        this.title = title;
        this.description = description;
        this.alertness = alertness;
        this.upazila = upazila;
        this.healthAuthorization = healthAuthorization;
    }
}
