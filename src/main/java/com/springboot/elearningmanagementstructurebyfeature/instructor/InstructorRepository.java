package com.springboot.elearningmanagementstructurebyfeature.instructor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

    Optional<Instructor> findByIdAndNationalIdCard(Integer id, String nationalIdCard);

    List<Instructor> findByFamilyNameIgnoreCase(String familyName);

    List<Instructor> findByGivenNameIgnoreCase(String givenName);

    List<Instructor> findByBiographyContainsIgnoreCase(String biography);
}
