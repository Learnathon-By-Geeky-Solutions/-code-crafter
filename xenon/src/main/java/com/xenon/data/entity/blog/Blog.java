package com.xenon.data.entity.blog;

import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @Column(length = 1000, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private POST_CATEGORY category;

    @Column(length = 255)
    private String media;

    private LocalDate date = LocalDate.now();

    public Blog(String title, String content, POST_CATEGORY category, String media, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.media = media;
        this.user = user;
    }
}
