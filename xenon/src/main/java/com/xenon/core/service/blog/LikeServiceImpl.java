package com.xenon.core.service.blog;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.blog.CreateLikeRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.blog.Blog;
import com.xenon.data.repository.BlogRepository;
import com.xenon.data.repository.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl extends BaseService implements LikeService {

    private final LikeRepository likeRepository;
    private final BlogRepository blogRepository;

    @Override
    @Transactional
    public ResponseEntity<?> createLikeRequest(CreateLikeRequest body) {
        Blog blog = blogRepository.findById(body.getBlogId()).orElseThrow(() -> new ClientException("Post not found"));

        try {
            if (likeRepository.existsByBlog_IdAndUser_Id(body.getBlogId(), getCurrentUser().getId())) {
                likeRepository.deleteByBlog_IdAndUser_Id(body.getBlogId(), getCurrentUser().getId());
                return success("Like deleted successfully", null);
            }

            likeRepository.save(body.toEntity(getCurrentUser(), blog));
            return success("Like created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

}
