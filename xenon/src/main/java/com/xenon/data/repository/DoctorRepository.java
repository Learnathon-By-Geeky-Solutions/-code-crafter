package com.xenon.data.repository;

import com.xenon.data.entity.doctor.Doctor;
import com.xenon.data.entity.doctor.SpecialistCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByRegistrationNo(String registrationNumber);

    boolean existsByNid(String nid);

    boolean existsByPassport(String passport);


    Optional<Doctor> findByUserId(Long userId);
}
