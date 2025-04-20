package com.xenon.core.service.blog;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.request.blog.CreateBlogPostRequest;
import com.xenon.core.service.BaseService;
import com.xenon.data.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogServiceImpl extends BaseService implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public ResponseEntity<?> createBlogPostRequest(CreateBlogPostRequest body) {
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
    public ResponseEntity<?> getBlogPosts() {
        return null;
    }

    private void validateCreateBlogPostRequest(CreateBlogPostRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getTitle())) throw requiredField("Title");
        if (isNullOrBlank(body.getContent())) throw requiredField("Content");
        if (body.getCategory() == null) throw requiredField("Category");
    }
}
