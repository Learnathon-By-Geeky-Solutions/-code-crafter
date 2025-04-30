package com.xenon.core.domain.request.blog;

import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.doctorArticle.DoctorArticleCategory;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DoctorArticlePost {

    private String title;
    private String content;
    private DoctorArticleCategory doctorCategory;
    private String media;

    public Blog toEntity(User user) {
        return new Blog(title, content, doctorCategory, media, user);
    }
}
