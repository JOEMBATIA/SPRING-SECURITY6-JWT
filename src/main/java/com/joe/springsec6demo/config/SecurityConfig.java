package com.joe.springsec6demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.joe.springsec6demo.entity.Permission.*;
import static com.joe.springsec6demo.entity.Role.ADMIN;
import static com.joe.springsec6demo.entity.Role.MEMBER;
import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private static final String [] WHITE_LIST_URLS = {
            "/api/v1/auth/register",
            "/api/v1/auth/authenticate"
    };

    private static final String MANAGEMENT_URL = "/api/v1/auth/management/**";

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URLS)
                                .permitAll()
                                .requestMatchers(MANAGEMENT_URL).hasAnyRole(
                                        ADMIN.name(), MEMBER.name()
                                )
                                .requestMatchers(GET, MANAGEMENT_URL).hasAnyAuthority(
                                        ADMIN_READ.name(), MEMBER_READ.name()
                                )
                                .requestMatchers(POST, MANAGEMENT_URL).hasAnyAuthority(
                                        ADMIN_CREATE.name(), MEMBER_READ.name()
                                )
                                .requestMatchers(PUT, MANAGEMENT_URL).hasAnyAuthority(
                                        ADMIN_UPDATE.name(), MEMBER_UPDATE.name()
                                )
                                .requestMatchers(DELETE, MANAGEMENT_URL).hasAnyAuthority(
                                        ADMIN_DELETE.name(), MEMBER_DELETE.name()
                                )
                                .anyRequest()
                                .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
