package com.springboot.elearningmanagementstructurebyfeature.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginDto(

        @NotBlank
                @Email
        String email,
        @NotBlank
        String password
) {
}
