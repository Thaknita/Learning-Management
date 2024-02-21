package com.springboot.elearningmanagementstructurebyfeature.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CourseCreationDto(
        @NotBlank
         String title,
         @NotBlank
         String thumbnail,
        @NotBlank
         String description,
        @NotNull
         Boolean isFree,
         @NotNull
        Integer categoryId,
         @NotNull
        Integer instructorId

) {

}
