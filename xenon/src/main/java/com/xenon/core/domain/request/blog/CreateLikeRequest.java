package com.xenon.core.domain.request.blog;

import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.Like;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLikeRequest {

    private Long blogId;
    private Long userId;

    public Like toEntity(User user, Blog blog) {
        return new Like(user, blog);
    }
}
