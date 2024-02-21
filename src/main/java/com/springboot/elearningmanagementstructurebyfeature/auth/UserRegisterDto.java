package com.springboot.elearningmanagementstructurebyfeature.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserRegisterDto(
        @NotBlank
        String username,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        String confirmPassword,
        @NotNull
        Set<@NotEmpty String> roleName


) {
}
