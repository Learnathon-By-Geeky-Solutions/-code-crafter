package com.xenon.core.service.blog;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.blog.like.LikeStatus;
import com.xenon.core.domain.request.user.UserResponseRequest;
import com.xenon.core.domain.response.blog.like.LikeResponseRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.Like;
import com.xenon.data.entity.user.User;
import com.xenon.data.repository.BlogRepository;
import com.xenon.data.repository.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public ResponseEntity<?> getLikeStatus(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ClientException("Blog not found with id: " + blogId));



        boolean isLiked = likeRepository.findByUserAndBlog(getCurrentUser(), blog).isPresent();
        long likeCount = likeRepository.countByBlog(blog);

        return success("Like status retrieved successfully", new LikeStatus(isLiked, likeCount));
    }

    @Override
    public ResponseEntity<?> getLikesByBlogId(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ClientException("Blog not found with id: " + blogId));

        List<Like> likes = likeRepository.findByBlog(blog);

        List<UserResponseRequest> userResponseRequests = likes.stream()
                .map(like -> new UserResponseRequest(
                        like.getUser().getId(),
                        like.getUser().getFastName(),
                        like.getUser().getLastName(),
                        like.getUser().getEmail()
                ))
                .collect(Collectors.toList());

        LikeResponseRequest likeResponseRequest = new LikeResponseRequest(userResponseRequests.size(), userResponseRequests);

        return success("Likes retrieved successfully", likeResponseRequest);
    }

}
