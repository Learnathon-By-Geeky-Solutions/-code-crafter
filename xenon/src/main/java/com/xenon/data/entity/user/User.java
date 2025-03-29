package com.xenon.data.entity.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.response.user.UserResponse;
import com.xenon.data.entity.location.Upazila;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "TABLE_USER")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String fname;

    @Column(length = 30)
    private String lname;

    @Column(length = 20, nullable = false, unique = true)
    private String phone;

    @Column(length = 50, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role = UserRole.USER;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AccountStatus status = AccountStatus.INACTIVE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UPAZILA_ID")
    private Upazila upazila;

    private String area;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

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

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public UserResponse toResponse() {
        return new UserResponse(
                this.id,
                this.fname,
                this.lname,
                this.phone,
                this.email,
                this.role,
                this.status,
                this.upazila,
                this.area,
                this.latitude,
                this.longitude,
                this.gender
        );
    }

    @SneakyThrows
    public String toString(ObjectMapper objectMapper) {
        return objectMapper.writeValueAsString(this);
    }
}
