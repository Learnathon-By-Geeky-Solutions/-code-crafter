package com.xenon.data.repository;

import com.xenon.data.entity.blood.BloodCommentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodCommentRepository extends JpaRepository<BloodCommentTable, Long> {
    List<BloodCommentTable> findByBloodRequestPostIdIn(List<Long> postIds);

//    List<BloodCommentTable> findByBloodRequestPostId(Long postId);

}