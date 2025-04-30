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
    private Long blogId;  // Optional: for user's comment history
    private String blogTitle;  // Optional: for user's comment history

    // Constructor without blog info (for single blog comments)
    public CommentResponseRequest(Long id, Long userId, String userName, String content, ZonedDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.createdAt = createdAt;
    }
}