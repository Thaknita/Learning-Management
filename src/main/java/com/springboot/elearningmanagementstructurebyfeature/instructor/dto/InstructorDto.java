package com.springboot.elearningmanagementstructurebyfeature.instructor.dto;

import lombok.Builder;

@Builder
public record InstructorDto(
        Integer id,
        String familyName,
        String givenName,
        String biography

        )

{
}
