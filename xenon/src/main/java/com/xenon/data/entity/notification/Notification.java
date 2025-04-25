package com.xenon.data.entity.notification;

import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Column(nullable = false)
    private Boolean isRead;
    
    @Column(nullable = false)
    private String notificationType;
    
    @Column
    private Long relatedId;
    
    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
    }
    
    public Notification(User user, String title, String message, String notificationType, Long relatedId) {
        this.user = user;
        this.title = title;
        this.message = message;
        this.isRead = false;
        this.notificationType = notificationType;
        this.relatedId = relatedId;
    }
}