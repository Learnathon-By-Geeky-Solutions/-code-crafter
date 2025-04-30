package com.xenon.core.service.blog.doctorArticle;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.exception.UnauthorizedException;
import com.xenon.core.domain.request.blog.BlogPostRequest;
import com.xenon.core.domain.request.blog.DoctorArticlePost;
import com.xenon.core.service.blog.BlogService;
import com.xenon.core.service.common.BaseService;
import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.PostCategory;
import com.xenon.data.entity.blog.doctorArticle.DoctorArticleCategory;
import com.xenon.data.entity.user.UserRole;
import com.xenon.data.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorArticleServiceImpl extends BaseService implements DoctorArticleService {

    private final BlogService blogService;
    private final BlogRepository blogRepository;

    @Override
    public ResponseEntity<?> createArticle(DoctorArticlePost body) {

        validateCreateBlogPostRequest(body);

        if (getCurrentUser().getRole() != UserRole.DOCTOR)
            throw new UnauthorizedException("Only doctors can create articles");

        try {

            blogRepository.save(body.toEntity(getCurrentUser()));
            return success("Blog post created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }


    @Override
    public ResponseEntity<?> getAllArticles(Pageable pageable) {


        try {

            return success("Blog posts retrieved successfully", blogService.getBlogsByCategory(PostCategory.DOCTOR_ARTICLE.name(), pageable));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }


    @Override
    public ResponseEntity<?> searchArticles(String query, Pageable pageable) {
        Page<Blog> originalPage = blogRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                query, query, pageable);

        // Filter to only include doctor articles
        List<Blog> filteredContent = originalPage.getContent().stream()
                .filter(blog -> PostCategory.DOCTOR_ARTICLE.equals(blog.getCategory()))
                .collect(Collectors.toList());

        // Create a new Page object with the filtered content
        Page<Blog> filteredPage = new PageImpl<>(
                filteredContent,
                pageable,
                filteredContent.size()
        );

        return processArticlePage(filteredPage, pageable);
    }

    @Override
    public ResponseEntity<?> updateArticle(Long id, BlogPostRequest body) {
        if (isNotDoctor())
            throw new UnauthorizedException("Only doctors can update articles");

        body.setCategory(PostCategory.DOCTOR_ARTICLE.name());
        return blogService.updateBlog(id, body);
    }


    @Override
    public ResponseEntity<?> deleteArticle(Long id) {
        if (isNotDoctor() && getCurrentUser().getRole() != UserRole.ADMIN)
            throw new UnauthorizedException("Only doctors or admins can delete articles");

        return blogService.deleteBlog(id);
    }

    @Override
    public ResponseEntity<?> getArticleById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ClientException("Article not found with id: " + id));

        if (!PostCategory.DOCTOR_ARTICLE.equals(blog.getCategory())) {
            throw new ClientException("Blog with id: " + id + " is not a doctor article");
        }

        return blogService.getBlogById(id);
    }

    private ResponseEntity<?> processArticlePage(Page<Blog> blogPage, Pageable pageable) {
        try {
            return blogService.getAllBlogs(pageable);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private boolean isNotDoctor() {
        return getCurrentUser().getRole() != UserRole.DOCTOR;
    }

    private void validateCreateBlogPostRequest(DoctorArticlePost body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getTitle())) throw requiredField("Title");
        if (isNullOrBlank(body.getContent())) throw requiredField("Content");
        if (body.getDoctorCategory() == null) throw requiredField("Category");

    }

}