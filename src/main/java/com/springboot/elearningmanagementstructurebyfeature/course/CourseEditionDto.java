package com.springboot.elearningmanagementstructurebyfeature.course;
import com.springboot.elearningmanagementstructurebyfeature.category.CategoryDto;
import com.springboot.elearningmanagementstructurebyfeature.instructor.InstructorDto;
import lombok.Builder;

@Builder
public record CourseEditionDto(

        String title,

        String thumbnail,

        String description,

        Boolean isFree,

        CategoryDto category,

        InstructorDto instructor,

        Boolean isDeleted

) {


}
