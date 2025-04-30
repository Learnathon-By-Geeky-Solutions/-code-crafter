package com.xenon.core.service.blog;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.exception.UnauthorizedException;
import com.xenon.core.domain.request.blog.comment.CreateCommentRequest;
import com.xenon.core.domain.response.blog.comment.CommentResponseRequest;
import com.xenon.core.domain.response.PageResponseRequest;
import com.xenon.core.service.common.BaseService;
import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.Comment;
import com.xenon.data.repository.BlogRepository;
import com.xenon.data.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl extends BaseService implements CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    @Override
    public ResponseEntity<?> createCommentRequest(Long blogId, CreateCommentRequest body) {
        validateCreateCommentRequest(body);

        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ClientException("Blog not found"));

        try {
            Comment comment = commentRepository.save(body.toEntity(getCurrentUser(), blog));

            CommentResponseRequest commentResponseRequest = new CommentResponseRequest(
                    comment.getId(),
                    getCurrentUser().getId(),
                    getCurrentUser().getFirstName() + " " + getCurrentUser().getLastName(),
                    comment.getContent(),
                    comment.getCreatedAt()
            );

            return success("Comment created successfully", commentResponseRequest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

   /* @Override
    public ResponseEntity<?> getCommentsByBlogId(Long blogId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ClientException("Blog not found"));

        List<Comment> comments = commentRepository.findByBlogOrderByCreatedAtDesc(blog);

        List<CommentResponseRequest> commentResponseRequests = comments.stream()
                .map(comment -> new CommentResponseRequest(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getUser().getFirstName() + " " + comment.getUser().getLastName(),
                        comment.getContent(),
                        comment.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return success("Comments retrieved successfully", commentResponseRequests);
    }

    @Override
    public ResponseEntity<?> getCommentsByBlogId(Long blogId, Pageable pageable) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ClientException("Blog not found"));

        Page<Comment> commentPage = commentRepository.findByBlogOrderByCreatedAtDesc(blog, pageable);

        List<CommentResponseRequest> commentResponseRequests = commentPage.getContent().stream()
                .map(comment -> new CommentResponseRequest(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getUser().getFirstName() + " " + comment.getUser().getLastName(),
                        comment.getContent(),
                        comment.getCreatedAt()
                ))
                .collect(Collectors.toList());

        PageResponseRequest<CommentResponseRequest> pageResponseRequest = new PageResponseRequest<>(
                commentResponseRequests,
                commentPage.getNumber(),
                commentPage.getSize(),
                commentPage.getTotalElements(),
                commentPage.getTotalPages()
        );

        return success("Comments retrieved successfully", pageResponseRequest);
    }

    @Override
    public ResponseEntity<?> getRecentCommentsByBlogId(Long blogId, int limit) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ClientException("Blog not found"));

        List<Comment> comments = commentRepository.findTopNCommentsByBlog(blog, limit);

        List<CommentResponseRequest> commentResponseRequests = comments.stream()
                .map(comment -> new CommentResponseRequest(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getUser().getFirstName() + " " + comment.getUser().getLastName(),
                        comment.getContent(),
                        comment.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return success("Recent comments retrieved successfully", commentResponseRequests);
    }

    @Override
    public ResponseEntity<?> getUserComments(Pageable pageable) {
        Page<Comment> commentPage = commentRepository.findByUserOrderByCreatedAtDesc(getCurrentUser(), pageable);

        List<CommentResponseRequest> commentResponseRequests = commentPage.getContent().stream()
                .map(comment -> new CommentResponseRequest(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getUser().getFirstName() + " " + comment.getUser().getLastName(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getBlog().getId(),
                        comment.getBlog().getTitle()
                ))
                .collect(Collectors.toList());

        PageResponseRequest<CommentResponseRequest> pageResponseRequest = new PageResponseRequest<>(
                commentResponseRequests,
                commentPage.getNumber(),
                commentPage.getSize(),
                commentPage.getTotalElements(),
                commentPage.getTotalPages()
        );

        return success("User comments retrieved successfully", pageResponseRequest);
    }*/

    @Override
    @Transactional
    public ResponseEntity<?> updateComment(Long commentId, CreateCommentRequest body) {
        validateCreateCommentRequest(body);

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ClientException("Comment not found with id: " + commentId));

        if (!comment.getUser().getId().equals(getCurrentUser().getId())) {
            throw new UnauthorizedException("You are not authorized to update this comment");
        }

        comment.setContent(body.getContent());

        try {
            commentRepository.save(comment);
            CommentResponseRequest commentResponseRequest = new CommentResponseRequest(
                    comment.getId(),
                    getCurrentUser().getId(),
                    getCurrentUser().getFirstName() + " " + getCurrentUser().getLastName(),
                    comment.getContent(),
                    comment.getCreatedAt());

            return success("Comment updated successfully", commentResponseRequest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ClientException("Comment not found with id: " + commentId));

        // Check if the current user is the owner of the comment or an admin
        if (!comment.getUser().getId().equals(getCurrentUser().getId()) &&
                !getCurrentUser().getRole().name().equals("ADMIN")) {
            throw new UnauthorizedException("You are not authorized to delete this comment");
        }

        try {
            commentRepository.delete(comment);
            return success("Comment deleted successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

   /* @Override
    public ResponseEntity<?> countCommentsByBlogId(Long blogId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ClientException("Blog not found"));
        long count = commentRepository.countByBlog(blog);
        return success("Comment count retrieved successfully", count);
    }*/

    private void validateCreateCommentRequest(CreateCommentRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getContent())) throw requiredField("Comment");

        if (body.getContent().length() > 300) {
            throw clientException("Comment content cannot exceed 300 characters");
        }
    }
}
