package com.xenon.core.domain.request.blog;


import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.POST_CATEGORY;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateBlogPostRequest {

    private String title;
    private String content;
    private POST_CATEGORY category;
    private String media;

    public Blog toEntity(User user) {
        return new Blog(title, content, category, media, user);
    }
}
