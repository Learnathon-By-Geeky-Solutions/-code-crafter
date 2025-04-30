package com.xenon.core.service.blog;

import com.xenon.core.domain.request.blog.comment.CreateCommentRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
    ResponseEntity<?> createCommentRequest(Long blogId, CreateCommentRequest body);

   /* ResponseEntity<?> getCommentsByBlogId(Long blogId);

    ResponseEntity<?> getCommentsByBlogId(Long blogId, Pageable pageable);

    ResponseEntity<?> getRecentCommentsByBlogId(Long blogId, int limit);

    ResponseEntity<?> getUserComments(Pageable pageable);*/

    @Transactional
    ResponseEntity<?> updateComment(Long commentId, CreateCommentRequest commentDto);

    @Transactional
    ResponseEntity<?> deleteComment(Long commentId);

//    ResponseEntity<?> countCommentsByBlogId(Long blogId);
}