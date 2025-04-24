package com.xenon.core.domain.response.blog.like;

import com.xenon.core.domain.request.user.UserResponseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponseRequest {
    private int totalLikes;
    private List<UserResponseRequest> users;
}