package com.xenon.data.repository;

import com.xenon.core.domain.response.blood.projection.BloodMetaDataProjection;
import com.xenon.data.entity.blood.BloodRequestPost;
import com.xenon.data.entity.donor.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {

    Optional<Donor> findByUserId(Long userId);

    boolean existsByUserId(Long id);


}
