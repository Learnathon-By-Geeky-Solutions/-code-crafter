package com.xenon.core.service.blog.doctorArticle;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.exception.UnauthorizedException;
import com.xenon.core.domain.request.blog.BlogPostRequest;
import com.xenon.core.service.common.BaseService;
import com.xenon.core.service.blog.BlogService;
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

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorArticleServiceImpl extends BaseService implements DoctorArticleService {

    private final BlogService blogService;
    private final BlogRepository blogRepository;

    @Override
    public ResponseEntity<?> createArticle(BlogPostRequest body, DoctorArticleCategory doctorCategory) {
        if (getCurrentUser().getRole() != UserRole.DOCTOR)
            throw new UnauthorizedException("Only doctors can create articles");

        try {
            // Debug: Print the received doctorCategory
            log.info("Received doctorCategory enum: {}", doctorCategory);
            log.info("Received doctorCategory name: {}", doctorCategory.name());
            log.info("Received doctorCategory ordinal: {}", doctorCategory.ordinal());

            body.setCategory(PostCategory.DOCTOR_ARTICLE.name());
            // Use the enum name directly
            body.setDoctorCategory(doctorCategory.name());

            log.info("Setting doctor article with category={}, doctorCategory={}",
                    body.getCategory(), body.getDoctorCategory());

            // Create the blog entity manually to ensure correct doctor category
            Blog blog = new Blog(
                    body.getTitle(),
                    body.getContent(),
                    PostCategory.DOCTOR_ARTICLE,
                    doctorCategory,  // Pass the enum directly
                    body.getMedia(),
                    getCurrentUser()
            );

            // Save and verify the created entity
            Blog savedBlog = blogRepository.save(blog);
            log.info("Saved blog with doctorCategory: {}", savedBlog.getDoctorCategory());

            return success("Blog post created successfully", null);
        } catch (Exception e) {
            log.error("Error creating doctor article: " + e.getMessage(), e);
            throw new ApiException(e);
        }
    }


    /*@Override
    public ResponseEntity<?> createArticleWithCategory(BlogPostRequest body, DoctorArticleCategory doctorCategory) {
        if (getCurrentUser().getRole() != UserRole.DOCTOR)
            throw new UnauthorizedException("Only doctors can create articles");

        if (doctorCategory == null)throw clientException("Invalid doctor category: " + doctorCategory);

        try {
            body.setCategory(PostCategory.DOCTOR_ARTICLE.name());
            body.setDoctorCategory(doctorCategory);
            return success("Blog post created successfully", null);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }

    }*/

    @Override
    public ResponseEntity<?> getAllArticles(Pageable pageable) {
        return blogService.getBlogsByCategory(PostCategory.DOCTOR_ARTICLE.name(), pageable);
    }

    @Override
    public ResponseEntity<?> getArticlesByDoctorCategory(String doctorCategory, Pageable pageable) {
        try {
            // Validate doctor category
            DoctorArticleCategory.valueOf(doctorCategory);

            Page<Blog> blogPage = blogRepository.findByCategoryAndDoctorCategory(
                    PostCategory.DOCTOR_ARTICLE, doctorCategory, pageable);

            return processArticlePage(blogPage, pageable);
        } catch (IllegalArgumentException e) {
            throw clientException("Invalid doctor category: " + doctorCategory);
        }
    }

    /*@Override
    public ResponseEntity<?> getTrendingArticles(String trendingBy, Pageable pageable) {
        Page<Blog> originalPage;

        if ("comments".equalsIgnoreCase(trendingBy)) {
            // Get blogs with most comments in the last 7 days
            ZonedDateTime weekAgo = ZonedDateTime.now().minusDays(7);
            originalPage = blogRepository.findTrendingByComments(weekAgo, pageable);
        } else if ("likes".equalsIgnoreCase(trendingBy)) {
            // Get blogs with most likes
            originalPage = blogRepository.findTrendingByLikes(pageable);
        } else if ("views".equalsIgnoreCase(trendingBy)) {
            // Get blogs ordered by view count (default sort)
            originalPage = blogRepository.findAll(pageable);
        } else {
            throw clientException("Invalid trending parameter: " + trendingBy);
        }

        // Filter to only include doctor articles
        List<Blog> filteredContent = originalPage.getContent().stream()
                .filter(blog -> PostCategory.DOCTOR_ARTICLE.equals(blog.getCategory()))
                .collect(Collectors.toList());

        // Create a new Page object with the filtered content
        Page<Blog> filteredPage = new PageImpl<>(
                filteredContent,
                pageable,
                filteredContent.size() // Note: this doesn't reflect true total count in database
        );

        return processArticlePage(filteredPage, pageable);
    }
*/
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
    public ResponseEntity<?> updateArticleWithCategory(Long id, BlogPostRequest body, DoctorArticleCategory doctorCategory) {
        if (isNotDoctor())
            throw new UnauthorizedException("Only doctors can update articles");

       if (doctorCategory == null)throw clientException("Invalid doctor category: " + doctorCategory);

       try {
           body.setCategory(PostCategory.DOCTOR_ARTICLE.name());
           body.setDoctorCategory(String.valueOf(doctorCategory));
           return blogService.updateBlog(id, body);
       }catch (Exception e) {
           log.error(e.getMessage(), e);
           throw new ApiException(e);
       }


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
}