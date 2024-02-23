package com.springboot.elearningmanagementstructurebyfeature.course;

import com.springboot.elearningmanagementstructurebyfeature.category.CategoryDto;
import com.springboot.elearningmanagementstructurebyfeature.instructor.dto.InstructorDto;
import lombok.Builder;

@Builder
public record CourseDto(
        Long id,
        String title,
        String description,
        String thumbnail,
        Boolean isFree,
        CategoryDto category,
        InstructorDto instructor,
        Boolean isDeleted
) {
}
