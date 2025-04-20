package com.xenon.core.service.blog;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.blog.CreateCommentRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.blog.Blog;
import com.xenon.data.repository.BlogRepository;
import com.xenon.data.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl extends BaseService implements CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;


    @Override
    public ResponseEntity<?> createCommentRequest(CreateCommentRequest body) {

        validateCreateCommentRequest(body);

        Blog blog = blogRepository.findById(body.getBlogId()).orElseThrow(() -> new ClientException("Blog not found"));

        try {
            commentRepository.save(body.toEntity(getCurrentUser(), blog));
            return success("Comment created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void validateCreateCommentRequest(CreateCommentRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getContent())) throw requiredField("Comment");
    }
}
