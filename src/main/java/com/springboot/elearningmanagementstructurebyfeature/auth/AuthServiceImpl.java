package com.springboot.elearningmanagementstructurebyfeature.auth;
import com.springboot.elearningmanagementstructurebyfeature.user.User;
import com.springboot.elearningmanagementstructurebyfeature.user.UserService;
import com.springboot.elearningmanagementstructurebyfeature.util.RandomSixDigit;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final UserRegistrationMapper userRegistrationMapper;
    private final JavaMailSender javaMailSender;
    private final AuthRepository authRepository;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtEncoder jwtEncoder;
    private AuthDto creatAccessToken(Authentication authentication){
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(authentication.getName())
                .audience(List.of("Mobile", "Web"))
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.MINUTES))
                .issuer(authentication.getName())
                .subject("access token")
                .claim("scope", scope)
                .build();
        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        return AuthDto.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .build();
    }
    //inject properties application
    @Value("${spring.mail.username}")
    private String adminMail;

    @Override
    public AuthDto refresh(RefreshTokenDto refreshTokenDto) {
        return null;
    }

    @Override
    public AuthDto login(LoginDto loginDto) {

        Authentication auth = new UsernamePasswordAuthenticationToken(
                loginDto.email(),
                loginDto.password()
        );
        auth = daoAuthenticationProvider.authenticate(auth);
        log.info("Auth: {}", auth);
        log.info("Auth:{}", auth.getName());
        log.info("Auth: {}", auth.getAuthorities());
        return this.creatAccessToken(auth);
    }
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
