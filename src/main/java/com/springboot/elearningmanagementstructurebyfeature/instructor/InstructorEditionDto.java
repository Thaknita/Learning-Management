package com.springboot.elearningmanagementstructurebyfeature.instructor;

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
