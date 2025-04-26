package com.xenon.data.repository;

import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.PostCategory;
import com.xenon.data.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findAllByUser(User user, Pageable pageable);
    Page<Blog> findAllByCategory(PostCategory category, Pageable pageable);

    // Search functionality
    Page<Blog> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String titleQuery, String contentQuery, Pageable pageable);

    // Search by category
    Page<Blog> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseAndCategory(
            String titleQuery, String contentQuery, PostCategory category, Pageable pageable);

    // Doctor article specific queries
    Page<Blog> findByCategoryAndDoctorCategory(PostCategory category, String doctorCategory, Pageable pageable);

    // Trending blogs by recent comments
    @Query("SELECT b FROM Blog b LEFT JOIN Comment c ON b.id = c.blog.id " +
            "WHERE c.createdAt > :since GROUP BY b.id ORDER BY COUNT(c.id) DESC")
    Page<Blog> findTrendingByComments(@Param("since") ZonedDateTime since, Pageable pageable);

    // Trending blogs by recent likes
    @Query("SELECT b FROM Blog b LEFT JOIN Like l ON b.id = l.blog.id " +
            "GROUP BY b.id ORDER BY COUNT(l.id) DESC")
    Page<Blog> findTrendingByLikes(Pageable pageable);

    // Featured blogs
    Page<Blog> findByIsFeaturedTrue(Pageable pageable);

    // Find featured by category
    Page<Blog> findByIsFeaturedTrueAndCategory(PostCategory category, Pageable pageable);

    // Find doctor articles with specific doctor category
    Page<Blog> findByCategoryAndDoctorCategoryAndIsFeaturedTrue(
            PostCategory category, String doctorCategory, Pageable pageable);
}