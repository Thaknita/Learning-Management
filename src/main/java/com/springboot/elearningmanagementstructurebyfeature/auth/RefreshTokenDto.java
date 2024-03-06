package com.springboot.elearningmanagementstructurebyfeature.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDto(
        @NotBlank
        String refreshToken
) {
}
