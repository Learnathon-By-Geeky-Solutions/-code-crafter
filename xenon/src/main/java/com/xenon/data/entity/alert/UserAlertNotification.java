package com.xenon.data.entity.alert;

import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "user_alert_notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAlertNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ALERT_ID", nullable = false)
    private AlertTable alert;
    
    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;
    
    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;
    
    @Column(name = "read_at")
    private ZonedDateTime readAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
    }
    
    public void markAsRead() {
        this.isRead = true;
        this.readAt = ZonedDateTime.now();
    }
    
    public UserAlertNotification(User user, AlertTable alert) {
        this.user = user;
        this.alert = alert;
        this.createdAt = ZonedDateTime.now();
    }
}