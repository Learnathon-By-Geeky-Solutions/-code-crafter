package com.xenon.data.repository;

import com.xenon.data.entity.blog.Blog;
import com.xenon.data.entity.blog.Like;
import com.xenon.data.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndBlog(User user, Blog blog);
    List<Like> findByBlog(Blog blog);
    long countByBlog(Blog blog);
    void deleteAllByBlog(Blog blog);
}
