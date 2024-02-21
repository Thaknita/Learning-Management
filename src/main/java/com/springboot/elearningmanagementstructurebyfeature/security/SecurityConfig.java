package com.springboot.elearningmanagementstructurebyfeature.security;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userDetailService);
        dao.setPasswordEncoder(passwordEncoder);
        return dao;
    }

    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity) throws Exception {

        //permit all request
        // httpSecurity.authorizeHttpRequests(request -> request.anyRequest().permitAll());

        /*To respect REST architecture we will never store any csrf token on server
            then make our api to stateless .
            - so we disable csrf -- httpSecurity.csrf(AbstractHttpConfigurer::disable);
            -turn api to stateless -- httpSecurity.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            -change security mechanism for stateless -- .httpBasic(Customizer.withDefaults()); after below state
            httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/auth/**")) tell spring security to not checking this end point
         */
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(session-> session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
        ));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers(
                                "/api/v1/auth/**",
                                "files/**",
                                "api/v1/courses"
                                ).permitAll()
                        .anyRequest().authenticated()

                )
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
