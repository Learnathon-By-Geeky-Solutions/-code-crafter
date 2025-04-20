package com.xenon.data.repository;

import com.xenon.data.entity.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByRegistrationNo(String registrationNumber);

    boolean existsByNid(String nid);

    boolean existsByPassport(String passport);
}
