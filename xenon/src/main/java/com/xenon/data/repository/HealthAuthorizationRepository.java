package com.xenon.data.repository;

import com.xenon.data.entity.healthAuthorization.HealthAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthAuthorizationRepository extends JpaRepository<HealthAuthorization, Long> {
    boolean existsByAuthorizationNumber(String authorizationNumber);


    Optional<HealthAuthorization> findByUserId(Long id);
}
