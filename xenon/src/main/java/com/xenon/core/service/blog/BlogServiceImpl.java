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
import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.user.User;
import com.xenon.data.entity.user.UserRole;
import com.xenon.data.repository.BlogRepository;
import com.xenon.data.repository.CommentRepository;
import com.xenon.data.repository.DoctorRepository;
import com.xenon.data.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogServiceImpl extends BaseService implements BlogService {

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public ResponseEntity<?> createBlogPostRequest(BlogPostRequest body) {
        validateCreateBlogPostRequest(body);

        try {
            Blog blog = body.toEntity(getCurrentUser());

            // Check if this is an admin setting a featured post
            if (body.getIsFeatured() != null && !getCurrentUser().getRole().equals(UserRole.ADMIN)) {
                blog.setIsFeatured(false); // Only admins can set featured
            }

            blogRepository.save(blog);
            return success("Blog post created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAllBlogs(Pageable pageable) {
        Page<Blog> blogPage = blogRepository.findAll(pageable);
        return processBlogPage(blogPage, pageable);
    }

    @Override
    public ResponseEntity<?> getBlogsByCategory(String category, Pageable pageable) {
        try {
            PostCategory postCategory = PostCategory.valueOf(category.toUpperCase());
            Page<Blog> blogPage = blogRepository.findAllByCategory(postCategory, pageable);
            return processBlogPage(blogPage, pageable);
        } catch (IllegalArgumentException e) {
            throw clientException("Invalid category: " + category);
        }
    }

    @Override
    public ResponseEntity<?> getUserBlogs(Pageable pageable) {
        User user = userRepository.findById(getCurrentUser().getId())
                .orElseThrow(() -> clientException("User not found"));

        Page<Blog> blogPage = blogRepository.findAllByUser(user, pageable);
        return processBlogPage(blogPage, pageable);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateBlog(Long id, BlogPostRequest body) {
        validateCreateBlogPostRequest(body);

        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ClientException("Blog not found with id: " + id));

        if (!blog.getUser().getId().equals(getCurrentUser().getId()) &&
                !getCurrentUser().getRole().equals(UserRole.ADMIN)) {
            throw new UnauthorizedException("You are not authorized to update this blog");
        }

        blog.setTitle(body.getTitle());
        blog.setContent(body.getContent());
        blog.setCategory(PostCategory.valueOf(body.getCategory()));
        blog.setMedia(body.getMedia());

        if (body.getDoctorCategory() != null && !body.getDoctorCategory().isEmpty()) {
            blog.setDoctorCategory(body.getDoctorCategory());
        }

        // Only admin can update featured status
        if (body.getIsFeatured() != null && getCurrentUser().getRole().equals(UserRole.ADMIN)) {
            blog.setIsFeatured(body.getIsFeatured());
        }

        try {
            Blog savedBlog = blogRepository.save(blog);
            return success("Blog updated successfully", convertToBlogResponseRequest(savedBlog));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ClientException("Blog not found with id: " + id));

        if (!blog.getUser().getId().equals(getCurrentUser().getId()) &&
                !getCurrentUser().getRole().equals(UserRole.ADMIN)) {
            throw new UnauthorizedException("You are not authorized to delete this blog");
        }

        commentRepository.deleteAllByBlog(blog);
        likeRepository.deleteAllByBlog(blog);

        blogRepository.delete(blog);
        return success("Blog deleted successfully", null);
    }

    @Override
    public ResponseEntity<?> getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ClientException("Blog not found with id: " + id));

        return success("Blog retrieved successfully", convertToBlogResponseRequest(blog));
    }

    @Override
    public ResponseEntity<?> searchBlogs(String query, Pageable pageable) {
        Page<Blog> blogPage = blogRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                query, query, pageable);

        return processBlogPage(blogPage, pageable);
    }

    @Override
    public ResponseEntity<?> searchBlogsByCategory(String query, String category, Pageable pageable) {
        try {
            PostCategory postCategory = PostCategory.valueOf(category.toUpperCase());
            Page<Blog> blogPage = blogRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseAndCategory(
                    query, query, postCategory, pageable);

            return processBlogPage(blogPage, pageable);
        } catch (IllegalArgumentException e) {
            throw clientException("Invalid category: " + category);
        }
    }

    @Override
    public ResponseEntity<?> getTrendingBlogs(String trendingBy, Pageable pageable) {
        Page<Blog> blogPage;

        if ("comments".equalsIgnoreCase(trendingBy)) {
            // Get blogs with most comments in the last 7 days
            ZonedDateTime weekAgo = ZonedDateTime.now().minusDays(7);
            blogPage = blogRepository.findTrendingByComments(weekAgo, pageable);
        } else if ("likes".equalsIgnoreCase(trendingBy)) {
            // Get blogs with most likes
            blogPage = blogRepository.findTrendingByLikes(pageable);
        } else if ("views".equalsIgnoreCase(trendingBy)) {
            // Get blogs ordered by view count (default sort)
            blogPage = blogRepository.findAll(pageable);
        } else {
            throw clientException("Invalid trending parameter: " + trendingBy);
        }

        return processBlogPage(blogPage, pageable);
    }

    @Override
    public ResponseEntity<?> getFeaturedBlogs(Pageable pageable) {
        Page<Blog> blogPage = blogRepository.findByIsFeaturedTrue(pageable);
        return processBlogPage(blogPage, pageable);
    }

    @Override
    public ResponseEntity<?> getFeaturedBlogsByCategory(String category, Pageable pageable) {
        try {
            PostCategory postCategory = PostCategory.valueOf(category.toUpperCase());
            Page<Blog> blogPage = blogRepository.findByIsFeaturedTrueAndCategory(postCategory, pageable);

            return processBlogPage(blogPage, pageable);
        } catch (IllegalArgumentException e) {
            throw clientException("Invalid category: " + category);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> setFeaturedStatus(Long id, boolean isFeatured) {
        if (!getCurrentUser().getRole().equals(UserRole.ADMIN)) {
            throw new UnauthorizedException("Only admins can set featured status");
        }

        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ClientException("Blog not found with id: " + id));

        blog.setIsFeatured(isFeatured);
        blogRepository.save(blog);

        return success("Featured status updated successfully", null);
    }

    @Override
    @Transactional
    public ResponseEntity<?> incrementViewCount(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ClientException("Blog not found with id: " + id));

        blog.incrementViewCount();
        blogRepository.save(blog);

        return success("View count incremented", blog.getViewCount());
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

        // Validate doctor category if provided
        if (body.getDoctorCategory() != null && !body.getDoctorCategory().isEmpty()) {
            try {
                // Validate that the category exists
                Class.forName("com.xenon.data.entity.blog.DoctorArticleCategory")
                        .getMethod("valueOf", String.class)
                        .invoke(null, body.getDoctorCategory());
            } catch (Exception e) {
                throw clientException("Invalid doctor category: " + body.getDoctorCategory());
            }
        }
    }

    private ResponseEntity<?> processBlogPage(Page<Blog> blogPage, Pageable pageable) {
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

    private BlogResponseRequest convertToBlogResponseRequest(Blog blog) {
        Long blogId = blog.getId();

        long commentCount = commentRepository.countByBlog(blog);
        long likeCount = likeRepository.countByBlog(blog);

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

        // Get doctor credentials if this is a doctor's post
        String doctorCredentials = null;
        if (blog.getUser().getRole() == UserRole.DOCTOR) {
            Optional<Doctor> doctorOpt = doctorRepository.findByUserId(blog.getUser().getId());
            if (doctorOpt.isPresent()) {
                Doctor doctor = doctorOpt.get();
                doctorCredentials = doctor.getDoctorTitle() + " " +
                        doctor.getSpecialistCategory().name().replace("_", " ") +
                        ", " + doctor.getExperience() + " years exp.";
            }
        }

        return new BlogResponseRequest(
                blogId,
                blog.getUser().getId(),
                blog.getUser().getFirstName() + " " + blog.getUser().getLastName(),
                blog.getUser().getRole().toString(),
                blog.getTitle(),
                blog.getContent(),
                blog.getCategory(),
                blog.getDoctorCategory(),
                blog.getMedia(),
                commentCount,
                likeCount,
                blog.getViewCount() != null ? blog.getViewCount() : 0,
                blog.getIsFeatured() != null ? blog.getIsFeatured() : false,
                commentResponseRequests,
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                doctorCredentials
        );
    }
}