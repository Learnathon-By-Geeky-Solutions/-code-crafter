package com.xenon.core.service.blog;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.blog.like.LikeStatus;
import com.xenon.core.domain.request.user.UserResponseRequest;
import com.xenon.core.domain.response.PageResponseRequest;
import com.xenon.core.domain.response.blog.BlogResponseRequest;
import com.xenon.core.domain.response.blog.like.LikeResponseRequest;
import com.xenon.core.service.common.BaseService;
import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.Like;
import com.xenon.data.entity.blog.PostCategory;
import com.xenon.data.entity.user.User;
import com.xenon.data.repository.BlogRepository;
import com.xenon.data.repository.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl extends BaseService implements LikeService {

    private final LikeRepository likeRepository;
    private final BlogRepository blogRepository;

    @Override
    @Transactional
    public ResponseEntity<?> toggleLike(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ClientException("Blog not found with id: " + blogId));

        Optional<Like> existingLike = likeRepository.findByUserAndBlog(getCurrentUser(), blog);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return success("Blog unliked successfully", new LikeStatus(false, likeRepository.countByBlog(blog)));
        }

        try {
            Like like = new Like(getCurrentUser(), blog);
            likeRepository.save(like);
            return success("Blog liked successfully", new LikeStatus(true, likeRepository.countByBlog(blog)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

   /* @Override
    public ResponseEntity<?> getLikeStatus(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ClientException("Blog not found with id: " + blogId));

        boolean isLiked = likeRepository.findByUserAndBlog(getCurrentUser(), blog).isPresent();
        long likeCount = likeRepository.countByBlog(blog);

        return success("Like status retrieved successfully", new LikeStatus(isLiked, likeCount));
    }*/

    /*@Override
    public ResponseEntity<?> getLikesByBlogId(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ClientException("Blog not found with id: " + blogId));

        List<Like> likes = likeRepository.findByBlog(blog);

        List<UserResponseRequest> userResponseRequests = likes.stream()
                .map(like -> new UserResponseRequest(
                        like.getUser().getId(),
                        like.getUser().getFirstName(),
                        like.getUser().getLastName(),
                        like.getUser().getEmail()
                ))
                .collect(Collectors.toList());

        LikeResponseRequest likeResponseRequest = new LikeResponseRequest(userResponseRequests.size(), userResponseRequests);

        return success("Likes retrieved successfully", likeResponseRequest);
    }*/

    /*@Override
    public ResponseEntity<?> getLikesByBlogIdPaginated(Long blogId, Pageable pageable) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ClientException("Blog not found with id: " + blogId));

        Page<Like> likePage = likeRepository.findByBlog(blog, pageable);

        List<UserResponseRequest> userResponseRequests = likePage.getContent().stream()
                .map(like -> new UserResponseRequest(
                        like.getUser().getId(),
                        like.getUser().getFirstName(),
                        like.getUser().getLastName(),
                        like.getUser().getEmail()
                ))
                .collect(Collectors.toList());

        PageResponseRequest<UserResponseRequest> pageResponseRequest = new PageResponseRequest<>(
                userResponseRequests,
                likePage.getNumber(),
                likePage.getSize(),
                likePage.getTotalElements(),
                likePage.getTotalPages()
        );

        return success("Likes retrieved successfully", pageResponseRequest);
    }*/

   /* @Override
    public ResponseEntity<?> getMostLikedBlogs(Pageable pageable) {
        try {
            List<Object[]> results = likeRepository.findMostLikedBlogs(pageable);

            List<BlogResponseRequest> blogResponseRequests = results.stream()
                    .map(result -> {
                        Blog blog = (Blog) result[0];
                        Long likeCount = (Long) result[1];

                        BlogResponseRequest response = convertToBlogResponse(blog);
                        response.setLikeCount(likeCount);
                        return response;
                    })
                    .collect(Collectors.toList());

            return success("Most liked blogs retrieved successfully", blogResponseRequests);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }
*/
    /*@Override
    public ResponseEntity<?> getMostLikedBlogsByCategory(String category, Pageable pageable) {
        try {
            PostCategory postCategory = PostCategory.valueOf(category.toUpperCase());
            List<Object[]> results = likeRepository.findMostLikedBlogsByCategory(postCategory, pageable);

            List<BlogResponseRequest> blogResponseRequests = results.stream()
                    .map(result -> {
                        Blog blog = (Blog) result[0];
                        Long likeCount = (Long) result[1];

                        BlogResponseRequest response = convertToBlogResponse(blog);
                        response.setLikeCount(likeCount);
                        return response;
                    })
                    .collect(Collectors.toList());

            return success("Most liked blogs by category retrieved successfully", blogResponseRequests);
        } catch (IllegalArgumentException e) {
            throw clientException("Invalid category: " + category);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }*/

    @Override
    public ResponseEntity<?> getUserLikedBlogs(Pageable pageable) {
        User currentUser = getCurrentUser();
        Page<Like> likePage = likeRepository.findByUser(currentUser, pageable);

        List<BlogResponseRequest> blogResponseRequests = likePage.getContent().stream()
                .map(like -> convertToBlogResponse(like.getBlog()))
                .collect(Collectors.toList());

        PageResponseRequest<BlogResponseRequest> pageResponseRequest = new PageResponseRequest<>(
                blogResponseRequests,
                likePage.getNumber(),
                likePage.getSize(),
                likePage.getTotalElements(),
                likePage.getTotalPages()
        );

        return success("User liked blogs retrieved successfully", pageResponseRequest);
    }

    /*@Override
    public ResponseEntity<?> countLikesByBlogId(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ClientException("Blog not found with id: " + blogId));

        long likeCount = likeRepository.countByBlog(blog);
        return success("Like count retrieved successfully", likeCount);
    }*/

    // Helper method to convert Blog to BlogResponseRequest
    private BlogResponseRequest convertToBlogResponse(Blog blog) {
        // Create a simplified blog response with essential details
        return new BlogResponseRequest(
                blog.getId(),
                blog.getUser().getId(),
                blog.getUser().getFirstName() + " " + blog.getUser().getLastName(),
                blog.getUser().getRole().toString(),
                blog.getTitle(),
                blog.getContent(),
                blog.getCategory(),
                blog.getDoctorCategory(),
                blog.getMedia(),
                0, // commentCount - not needed here
                likeRepository.countByBlog(blog),
                blog.getViewCount() != null ? blog.getViewCount() : 0,
                null, // comments - not needed here
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                null // doctorCredentials - not needed here
        );
    }
}