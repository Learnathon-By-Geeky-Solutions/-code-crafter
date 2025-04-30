package com.xenon.data.entity.blood;

import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "blood_comment_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodCommentTable {

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

    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
        updatedAt = ZonedDateTime.now();
    }

    public BloodCommentTable(User user, BloodRequestPost bloodRequestPost, String comment) {
        this.user = user;
        this.bloodRequestPost = bloodRequestPost;
        this.comment = comment;
    }
}
