package com.xenon.core.domain.request.blog;

import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.Comment;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateCommentRequest {

    private Long blogId;
    private String content;

    public Comment toEntity(User user, Blog blog) {
        return new Comment(user, blog, content);
    }
}
