package com.xenon.data.repository;

import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.Like;
import com.xenon.data.entity.blog.PostCategory;
import com.xenon.data.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndBlog(User user, Blog blog);
    /*List<Like> findByBlog(Blog blog);
    Page<Like> findByBlog(Blog blog, Pageable pageable);*/
    Page<Like> findByUser(User user, Pageable pageable);
    long countByBlog(Blog blog);
    void deleteAllByBlog(Blog blog);

    // Find most liked blogs
    @Query("SELECT l.blog, COUNT(l) as likeCount FROM Like l GROUP BY l.blog ORDER BY likeCount DESC")
    List<Object[]> findMostLikedBlogs(Pageable pageable);

    // Find most liked blogs by category
    @Query("SELECT l.blog, COUNT(l) as likeCount FROM Like l WHERE l.blog.category = :category GROUP BY l.blog ORDER BY likeCount DESC")
    List<Object[]> findMostLikedBlogsByCategory(@Param("category") PostCategory category, Pageable pageable);

    // Find most liked doctor articles
    @Query("SELECT l.blog, COUNT(l) as likeCount FROM Like l WHERE l.blog.category = 'DOCTOR_ARTICLE' GROUP BY l.blog ORDER BY likeCount DESC")
    List<Object[]> findMostLikedDoctorArticles(Pageable pageable);

    // Find most liked doctor articles by doctor category
    @Query("SELECT l.blog, COUNT(l) as likeCount FROM Like l WHERE l.blog.category = 'DOCTOR_ARTICLE' AND l.blog.doctorCategory = :doctorCategory GROUP BY l.blog ORDER BY likeCount DESC")
    List<Object[]> findMostLikedDoctorArticlesByCategory(@Param("doctorCategory") String doctorCategory, Pageable pageable);

    // Count likes by user
    long countByUser(User user);

    // Get blogs liked within a date range
    @Query("SELECT l FROM Like l WHERE l.user = :user AND l.blog.createdAt BETWEEN :startDate AND :endDate")
    List<Like> findByUserAndBlogCreatedAtBetween(
            @Param("user") User user,
            @Param("startDate") java.time.ZonedDateTime startDate,
            @Param("endDate") java.time.ZonedDateTime endDate
    );
}