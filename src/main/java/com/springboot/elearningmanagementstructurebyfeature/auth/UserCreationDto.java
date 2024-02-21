package com.springboot.elearningmanagementstructurebyfeature.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;


@Builder
public record UserCreationDto(

        @NotBlank
        String username,
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password,

        @NotNull
        Set<@NotEmpty String> roleName




) {
}
