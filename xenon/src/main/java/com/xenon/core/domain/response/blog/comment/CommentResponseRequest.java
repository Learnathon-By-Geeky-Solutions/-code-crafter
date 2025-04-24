package com.xenon.core.domain.response.blog.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentResponseRequest {
    private Long id;
    private Long userId;
    private String userName;
    private String content;
    private ZonedDateTime createdAt;
}
