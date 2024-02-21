package com.springboot.elearningmanagementstructurebyfeature.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface CourseRepository extends
        JpaRepository <Course, Long> {
        List<Course> findByTitleContainingIgnoreCase(String title);
        List<Course> findByDescriptionContainingIgnoreCase(String description);
        List<Course> findAllByIsDeletedFalse();
        Optional<Course> findByIdAndIsDeletedFalse(Long id);
        @Modifying
        @Query("UPDATE Course as c SET c.isDeleted = true where c.id=?1")
        void  updateIsDeletedById(Long id);
}
