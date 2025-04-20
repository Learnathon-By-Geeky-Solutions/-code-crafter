package com.xenon.data.entity.blood;

import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blood_response")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BLOOD_REQUEST_POST_ID")
    private BloodRequestPost bloodRequestPost;

    @Column(nullable = false, length = 200)
    private String comment;

    public BloodResponse(User user, BloodRequestPost bloodRequestPost, String comment) {
        this.user = user;
        this.bloodRequestPost = bloodRequestPost;
        this.comment = comment;
    }
}
