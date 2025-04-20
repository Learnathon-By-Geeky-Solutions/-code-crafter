package com.xenon.data.repository;

import com.xenon.data.entity.healthAuthorization.AlertTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertTableRepository extends JpaRepository<AlertTable, Long> {
}
