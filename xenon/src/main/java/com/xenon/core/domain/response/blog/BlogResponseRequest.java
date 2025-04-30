package com.xenon.core.domain.response.blog;

import com.xenon.core.domain.response.blog.comment.CommentResponseRequest;
import com.xenon.data.entity.blog.PostCategory;
import com.xenon.data.entity.blog.doctorArticle.DoctorArticleCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BlogResponseRequest {

    private Long blogId;
    private Long userId;
    private String userName;
    private String userRole;  // Added to identify if content is from doctor
    private String title;
    private String content;
    private PostCategory category;
    private DoctorArticleCategory doctorCategory;  // For doctor articles
    private String media;
    private long commentCount;
    private long likeCount;
    private int viewCount;
    private List<CommentResponseRequest> commentResponseLists;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String doctorCredentials;  // Only for doctor articles
}