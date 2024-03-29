package com.springboot.elearningmanagementstructurebyfeature.auth;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/refresh")
    AuthDto refresh(@Valid @RequestBody RefreshTokenDto refreshTokenDto){
        return authService.refresh(refreshTokenDto);
    }
    @PostMapping("/login")
    AuthDto login(@Valid @RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    Map<String, Object> register(@Valid @RequestBody UserRegisterDto userRegisterDto) throws MessagingException {
        return authService.userRegistration(userRegisterDto);
    }
    @PostMapping("/verify")
    Map<String, Object> verify(@Valid @RequestBody VerifyDto verifyDto){
        return authService.verify(verifyDto);
    }

}
