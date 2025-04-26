package com.xenon.data.repository;

import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.Comment;
import com.xenon.data.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBlogOrderByCreatedAtDesc(Blog blog);
    Page<Comment> findByBlogOrderByCreatedAtDesc(Blog blog, Pageable pageable);
    Page<Comment> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    long countByBlog(Blog blog);
    void deleteAllByBlog(Blog blog);

    // Get top N recent comments for a blog
    @Query("SELECT c FROM Comment c WHERE c.blog = :blog ORDER BY c.createdAt DESC LIMIT :limit")
    List<Comment> findTopNCommentsByBlog(@Param("blog") Blog blog, @Param("limit") int limit);

    // Get comments within a date range for a blog
    @Query("SELECT c FROM Comment c WHERE c.blog = :blog AND c.createdAt BETWEEN :startDate AND :endDate ORDER BY c.createdAt DESC")
    List<Comment> findByBlogAndCreatedAtBetweenOrderByCreatedAtDesc(
            @Param("blog") Blog blog,
            @Param("startDate") ZonedDateTime startDate,
            @Param("endDate") ZonedDateTime endDate
    );

    // Count comments by user
    long countByUser(User user);

    // Get most commented blogs
    @Query("SELECT c.blog, COUNT(c) as commentCount FROM Comment c GROUP BY c.blog ORDER BY commentCount DESC")
    List<Object[]> findMostCommentedBlogs(Pageable pageable);
}