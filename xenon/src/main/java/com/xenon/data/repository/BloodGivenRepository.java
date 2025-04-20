package com.xenon.data.repository;

import com.xenon.data.entity.donor.BloodGiven;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodGivenRepository extends JpaRepository<BloodGiven, Long> {
}
