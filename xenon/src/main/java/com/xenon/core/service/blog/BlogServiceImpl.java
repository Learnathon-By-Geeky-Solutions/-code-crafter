package com.xenon.core.service.blog;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.exception.UnauthorizedException;
import com.xenon.core.domain.request.blog.BlogPostRequest;
import com.xenon.core.domain.response.blog.BlogResponseRequest;
import com.xenon.core.domain.response.blog.comment.CommentResponseRequest;
import com.xenon.core.domain.response.PageResponseRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.Comment;
import com.xenon.data.entity.blog.PostCategory;
import com.xenon.data.entity.user.User;
import com.xenon.data.repository.BlogRepository;
import com.xenon.data.repository.CommentRepository;
import com.xenon.data.repository.LikeRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogServiceImpl extends BaseService implements BlogService {

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final View error;

    @Override
    public ResponseEntity<?> createBlogPostRequest(BlogPostRequest body) {
        validateCreateBlogPostRequest(body);

        try {
            blogRepository.save(body.toEntity(getCurrentUser()));
            return success("Blog post created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAllBlogs(Pageable pageable) {

        Page<Blog> blogPage = blogRepository.findAll(pageable);

        List<BlogResponseRequest> blogResponseRequests = blogPage.getContent().stream()
                .map(this::convertToBlogResponseRequest)
                .collect(Collectors.toList());

        PageResponseRequest<BlogResponseRequest> pageResponseRequest = new PageResponseRequest<>(
                blogResponseRequests,
                blogPage.getNumber(),
                blogPage.getSize(),
                blogPage.getTotalElements(),
                blogPage.getTotalPages()
        );

        try {
            return success("Blog posts retrieved successfully", pageResponseRequest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getBlogsByCategory(String category, Pageable pageable) {
        try {
            PostCategory postCategory = PostCategory.valueOf(category.toUpperCase());
            Page<Blog> blogPage = blogRepository.findAllByCategory(postCategory, pageable);

            List<BlogResponseRequest> blogResponseRequests = blogPage.getContent().stream()
                    .map(this::convertToBlogResponseRequest)
                    .collect(Collectors.toList());

            PageResponseRequest<BlogResponseRequest> pageResponseRequest = new PageResponseRequest<>(
                    blogResponseRequests,
                    blogPage.getNumber(),
                    blogPage.getSize(),
                    blogPage.getTotalElements(),
                    blogPage.getTotalPages()
            );

            return success("Blogs by category retrieved successfully", pageResponseRequest);
        } catch (IllegalArgumentException e) {
            throw clientException("Invalid category: " + category);
        }
    }

    @Override
    public ResponseEntity<?> getUserBlogs(Pageable pageable) {
        User user = userRepository.findById(getCurrentUser().getId())
                .orElseThrow(() -> clientException("User not found"));

        Page<Blog> blogPage = blogRepository.findAllByUser(user, pageable);

        List<BlogResponseRequest> blogResponseRequests = blogPage.getContent().stream()
                .map(this::convertToBlogResponseRequest)
                .collect(Collectors.toList());

        PageResponseRequest<BlogResponseRequest> pageResponseRequest = new PageResponseRequest<>(
                blogResponseRequests,
                blogPage.getNumber(),
                blogPage.getSize(),
                blogPage.getTotalElements(),
                blogPage.getTotalPages()
        );

        try {
            return success("User blogs retrieved successfully", pageResponseRequest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateBlog(Long id, BlogPostRequest body) {
        validateCreateBlogPostRequest(body);

        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ClientException("Blog not found with id: " + id));

        if (!blog.getUser().getId().equals(getCurrentUser().getId())) {
            throw new  UnauthorizedException("You are not authorized to update this blog");
        }

        blog.setTitle(body.getTitle());
        blog.setContent(body.getContent());
        blog.setCategory(PostCategory.valueOf(body.getCategory()));
        blog.setMedia(body.getMedia());

        try {
            blogRepository.save(blog);
            return success("Blog updated successfully", convertToBlogResponseRequest(blog));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ClientException("Blog not found with id: " + id));

        if (!blog.getUser().getId().equals(getCurrentUser().getId())) {
            throw new UnauthorizedException ("You are not authorized to delete this blog");
        }

        commentRepository.deleteAllByBlog(blog);
        likeRepository.deleteAllByBlog(blog);

        blogRepository.delete(blog);
        return success("Blog deleted successfully", null);
    }

    private void validateCreateBlogPostRequest(BlogPostRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getTitle())) throw requiredField("Title");
        if (isNullOrBlank(body.getContent())) throw requiredField("Content");
        if (body.getCategory() == null) throw requiredField("Category");

        try {
            PostCategory.valueOf(body.getCategory());
        } catch (IllegalArgumentException e) {
            throw clientException("Invalid category: " + body.getCategory());
        }
    }
    private BlogResponseRequest convertToBlogResponseRequest(Blog blog) {
        Long blogId = blog.getId();

        long commentCount = commentRepository.countByBlog(blog);
        long likeCount = likeRepository.countByBlog(blog);

        List<Comment> comments = commentRepository.findByBlogOrderByCreatedAtDesc(blog);
        List<CommentResponseRequest> commentResponseRequests = comments.stream()
                .map(comment -> new CommentResponseRequest(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getUser().getFastName() + " " + comment.getUser().getLastName(),
                        comment.getContent(),
                        comment.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new BlogResponseRequest(
                blogId,
                blog.getUser().getId(),
                blog.getUser().getFastName() + " " + blog.getUser().getLastName(),
                blog.getTitle(),
                blog.getContent(),
                blog.getCategory(),
                blog.getMedia(),
                commentCount,
                likeCount,
                commentResponseRequests,
                blog.getCreatedAt(),
                blog.getUpdatedAt()
        );
    }
}
