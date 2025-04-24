package com.xenon.core.domain.request.blog.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeStatus {
    private boolean liked;
    private long likeCount;
}