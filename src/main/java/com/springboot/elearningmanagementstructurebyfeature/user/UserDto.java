package com.springboot.elearningmanagementstructurebyfeature.user;

import com.springboot.elearningmanagementstructurebyfeature.role.Role;

import java.util.Set;

public record UserDto(
        String name,
        String emailAddress,
        Set<Role> roles

) {
}
