package com.springboot.elearningmanagementstructurebyfeature.category;

import lombok.Builder;

@Builder
public record CategoryCreationDto(
        String name
) {
}
