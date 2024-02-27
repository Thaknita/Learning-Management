package com.springboot.elearningmanagementstructurebyfeature.security;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

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
    JwtAuthenticationProvider jwtAuthenticationProvider( @Qualifier("jwtRefreshDecoder") JwtDecoder jwtRefreshDecoder
    ,JwtAuthenticationConverter jwtAuthenticationConverter) {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtRefreshDecoder);
        provider.setJwtAuthenticationConverter(jwtAuthenticationConverter);
    return provider;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter (){
        return  new JwtAuthenticationConverter();

        //custom code here
    }
    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity, JwtAuthenticationConverter jwtAuthenticationConverter ) throws Exception {

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
                                "files/**"
                                ).permitAll()
                        .anyRequest().authenticated()
                )
               // .httpBasic(Customizer.withDefaults());
                .oauth2ResourceServer(oauth2-> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));
        return httpSecurity.build();
    }

    @Primary
    @Bean
    KeyPair jwtKeyPair() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Primary
    @Bean
     RSAKey jwtRsa(KeyPair keyPair){
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @Primary
    @Bean
    JwtDecoder jwtDecoder(RSAKey rsaKey){
        try {
            return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
    @Primary
    @Bean
    JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new JWKSource<SecurityContext>() {
            @Override
            public List<JWK> get(JWKSelector jwkSelector, SecurityContext securityContext) throws KeySourceException {
                return jwkSelector.select(jwkSet);
            }
        };
    }
    @Primary
    @Bean
    JwtEncoder jwtEncoder (JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }

    //=========================Refresh Token================================
    @Bean(name = "jwtRefreshKeyPair")
    KeyPair jwtRefreshKeyPair() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean(name = "jwtRefreshRsa")
    RSAKey jwtRefreshRsa(@Qualifier("jwtRefreshKeyPair") KeyPair keyPair){
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @Bean(name = "jwtRefreshDecoder")
    JwtDecoder jwtRefreshDecoder(@Qualifier("jwtRefreshRsa") RSAKey rsaKey){
        try {
            return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean(name = "jwkRefreshSource")
    JWKSource<SecurityContext> jwkRefreshSource(@Qualifier("jwtRefreshRsa") RSAKey rsaKey){
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new JWKSource<SecurityContext>() {
            @Override
            public List<JWK> get(JWKSelector jwkSelector, SecurityContext securityContext) throws KeySourceException {
                return jwkSelector.select(jwkSet);
            }
        };
    }
    @Bean(name = "jwtRefreshEncoder")
    JwtEncoder jwtRefreshEncoder (@Qualifier("jwkRefreshSource") JWKSource<SecurityContext> jwkSource){

        return new NimbusJwtEncoder(jwkSource);
    }



}
