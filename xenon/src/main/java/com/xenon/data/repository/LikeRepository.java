package com.xenon.data.repository;

import com.xenon.data.entity.blog.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByBlog_IdAndUser_Id(Long blogId, Long userId);
    void deleteByBlog_IdAndUser_Id(Long blogId, Long userId);
}
