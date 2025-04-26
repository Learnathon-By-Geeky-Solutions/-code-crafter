package com.xenon.core.domain.request.blog;

import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.PostCategory;
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
    private Boolean isFeatured;  // Optional: only for admins to set

    public Blog toEntity(User user) {
        Blog blog = new Blog(
                title,
                content,
                PostCategory.valueOf(category),
                media,
                user
        );

        if (doctorCategory != null && !doctorCategory.isEmpty()) {
            blog.setDoctorCategory(doctorCategory);
        }

        if (isFeatured != null) {
            blog.setIsFeatured(isFeatured);
        }

        return blog;
    }
}