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
    long countByBlog(Blog blog);
    void deleteAllByBlog(Blog blog);
}