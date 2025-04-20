package com.xenon.data.entity.healthAuthorization;

import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "health_authorization")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthAuthorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String authorizationNumber;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    public HealthAuthorization(String authorizationNumber, User user) {
        this.authorizationNumber = authorizationNumber;
        this.user = user;
    }
}
