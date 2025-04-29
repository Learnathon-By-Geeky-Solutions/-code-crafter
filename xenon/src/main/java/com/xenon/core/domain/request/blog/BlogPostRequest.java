package com.xenon.core.domain.request.blog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.PostCategory;
import com.xenon.data.entity.blog.doctorArticle.DoctorArticleCategory;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BlogPostRequest {

    private String title;
    private String content;
    private String category;
    private String doctorCategory;  // Optional: for doctor articles
    private String media;

    public Blog toEntity(User user) {
        Blog blog = new Blog(
                title,
                content,
                PostCategory.valueOf(category),
                media,
                user
        );

        // Only set doctor category if it's not null and valid
        if (doctorCategory != null && !doctorCategory.isEmpty()) {
            try {
                blog.setDoctorCategory(DoctorArticleCategory.valueOf(doctorCategory));
            } catch (IllegalArgumentException e) {
                // Skip setting the doctorCategory if it's invalid
                // This prevents constraint violations
            }
        }

        return blog;
    }
}