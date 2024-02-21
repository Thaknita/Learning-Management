package com.springboot.elearningmanagementstructurebyfeature.instructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;


@Builder
public record InstructorCreationDto(
        @NotBlank
       String familyName,
       @NotBlank
       String givenName,
       @NotBlank
        @Size( min=6, max = 30)
       String nationalIdCard,
        String biography

) {
}
