package com.xenon.data.repository;

import com.xenon.data.entity.location.Upazila;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UpazilaRepository extends JpaRepository<Upazila, Long> {
}
