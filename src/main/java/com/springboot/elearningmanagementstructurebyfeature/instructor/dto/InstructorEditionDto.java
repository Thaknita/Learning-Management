package com.springboot.elearningmanagementstructurebyfeature.instructor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record InstructorEditionDto(

        @NotBlank
        String familyName,
        @NotBlank
        String givenName,
        String biography
) {
}
