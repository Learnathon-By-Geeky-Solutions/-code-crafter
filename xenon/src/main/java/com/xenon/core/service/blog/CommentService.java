package com.xenon.core.service.blog;

import com.xenon.core.domain.request.blog.comment.CreateCommentRequest;
import com.xenon.core.domain.response.blog.comment.CommentResponseRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
    ResponseEntity<?> createCommentRequest(Long blogId, CreateCommentRequest body);

    ResponseEntity<?> getCommentsByBlogId(Long blogId);

    @Transactional
    ResponseEntity<?> updateComment(Long commentId, CreateCommentRequest commentDto);

    @Transactional
    ResponseEntity<?> deleteComment(Long commentId);
}
