package com.xenon.data.entity.blog;

import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "blog")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 3000, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PostCategory category;

    @Column(length = 30)
    private String doctorCategory;

    @Column(length = 255)
    private String media;

    @Column
    private Integer viewCount = 0;

    @Column(nullable = false)
    private Boolean isFeatured = false;

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

    public Blog(String title, String content, PostCategory category, String media, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.media = media;
        this.user = user;
        this.viewCount = 0;
        this.isFeatured = false;
    }

    public Blog(String title, String content, PostCategory category, String doctorCategory, String media, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.doctorCategory = doctorCategory;
        this.media = media;
        this.user = user;
        this.viewCount = 0;
        this.isFeatured = false;
    }

    public void incrementViewCount() {
        this.viewCount = (this.viewCount == null ? 1 : this.viewCount + 1);
    }
}