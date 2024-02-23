package com.springboot.elearningmanagementstructurebyfeature.auth;

import jakarta.mail.MessagingException;

import java.util.Map;


public interface AuthService {

    AuthDto login(LoginDto loginDto);

    Map<String, Object> userRegistration(UserRegisterDto userRegisterDto) throws MessagingException;

    Map<String, Object> verify(VerifyDto verifyDto);
}
