package com.xenon.data.repository;

import com.xenon.data.entity.blood.BloodRequestPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodRequestPostRepository extends JpaRepository<BloodRequestPost, Long> {
}
