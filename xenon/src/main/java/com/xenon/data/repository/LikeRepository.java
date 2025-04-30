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
    Page<Like> findByUser(User user, Pageable pageable);
    long countByBlog(Blog blog);
    void deleteAllByBlog(Blog blog);

}