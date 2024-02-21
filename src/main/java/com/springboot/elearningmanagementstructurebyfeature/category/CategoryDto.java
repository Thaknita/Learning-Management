package com.springboot.elearningmanagementstructurebyfeature.category;

import lombok.Builder;

@Builder
public record CategoryDto(

        Integer id,
        String name,
        Boolean isDeleted

) {
}
