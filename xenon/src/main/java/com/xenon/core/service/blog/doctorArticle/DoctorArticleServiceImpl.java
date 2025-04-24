package com.xenon.core.service.blog.doctorArticle;

import com.xenon.core.domain.exception.UnauthorizedException;
import com.xenon.core.domain.request.blog.BlogPostRequest;
import com.xenon.core.service.BaseService;
import com.xenon.core.service.blog.BlogService;
import com.xenon.data.entity.blog.PostCategory;
import com.xenon.data.entity.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorArticleServiceImpl extends BaseService implements DoctorArticleService {

    private final BlogService blogService;

    @Override
    public ResponseEntity<?> createArticle(BlogPostRequest body) {
        if (getCurrentUser().getRole() != UserRole.DOCTOR)
            throw new UnauthorizedException("Only doctors can create articles");

        body.setCategory(PostCategory.DOCTOR_ARTICLE.name());

        return blogService.createBlogPostRequest(body);
    }

    @Override
    public ResponseEntity<?> getAllArticles(Pageable pageable) {
        return blogService.getBlogsByCategory(PostCategory.DOCTOR_ARTICLE.name(), pageable);
    }


    @Override
    public ResponseEntity<?> updateArticle(Long id, BlogPostRequest body) {

        if (isNotDoctor()) throw new UnauthorizedException("Only doctors can delete articles");
        body.setCategory(PostCategory.DOCTOR_ARTICLE.name());
        return blogService.updateBlog(id, body);
    }

    @Override
    public ResponseEntity<?> deleteArticle(Long id) {

        if (isNotDoctor()) throw new UnauthorizedException("Only doctors can delete articles");
        return blogService.deleteBlog(id);
    }

    private boolean isNotDoctor() {
        return getCurrentUser().getRole() != UserRole.DOCTOR;
    }

}
