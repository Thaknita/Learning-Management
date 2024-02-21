package com.springboot.elearningmanagementstructurebyfeature.auth;

import com.springboot.elearningmanagementstructurebyfeature.user.User;
import com.springboot.elearningmanagementstructurebyfeature.user.UserService;
import com.springboot.elearningmanagementstructurebyfeature.util.RandomSixDigit;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRegistrationMapper userRegistrationMapper;
    private final JavaMailSender javaMailSender;
    private final AuthRepository authRepository;

    //inject properties application
    @Value("${spring.mail.username}")
    private String adminMail;
    @Transactional
    @Override
    public Map<String, Object> userRegistration(UserRegisterDto userRegisterDto) throws MessagingException {
        if (!userRegisterDto.password().equals(userRegisterDto.confirmPassword())){
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "Password doesn't match");
        }
        UserCreationDto users = userRegistrationMapper.fromUserRegistrationDto(userRegisterDto);
        userService.createUser(users);

        //update verified code
        String sixDigits = RandomSixDigit.getSixDigit();
        authRepository.updateVerifiedCode(userRegisterDto.email(), sixDigits);

        //Create Message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setSubject("Account Verification");
        mimeMessageHelper.setText(sixDigits);
        mimeMessageHelper.setTo(userRegisterDto.email());
        mimeMessageHelper.setFrom(adminMail);

        //Send Message
        javaMailSender.send(mimeMessage);

        return  Map.of("message", "Please Verify Via Email address",
                            "email", users.email()
                        );
    }
    @Override
    public Map<String, Object> verify(VerifyDto verifyDto) {

        User user = authRepository.findByEmailAndVerifyCode(verifyDto.email(), verifyDto.verifyCode())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Incorrect Verify code or email"));
        user.setIsVerified(true);
        user.setVerifyCode(null);
        authRepository.save(user);
        return Map.of(
                "message","Your account is successfully verified",
                    "email" , user.getEmail()
        );
    }
}
