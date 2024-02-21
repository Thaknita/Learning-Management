package com.springboot.elearningmanagementstructurebyfeature.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyDto(

        @NotBlank
        @Email
        String email,
        @NotBlank
        String verifyCode


) {
}
