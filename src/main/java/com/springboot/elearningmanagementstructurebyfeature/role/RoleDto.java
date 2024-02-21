package com.springboot.elearningmanagementstructurebyfeature.role;

import java.util.List;

public record RoleDto(
        String name,
        List<String> authorities

) {
}
