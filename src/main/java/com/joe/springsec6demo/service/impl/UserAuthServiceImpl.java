package com.joe.springsec6demo.service.impl;

import com.joe.springsec6demo.dto.AuthenticateRequest;
import com.joe.springsec6demo.dto.UserAuthResponse;
import com.joe.springsec6demo.dto.UserRequest;
import com.joe.springsec6demo.entity.UserEntity;
import com.joe.springsec6demo.error.CustomBadCredentialsException;
import com.joe.springsec6demo.repository.UserRepository;
import com.joe.springsec6demo.service.JwtService;
import com.joe.springsec6demo.service.UserAuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    @Transactional
    public UserAuthResponse register(UserRequest request) {

        var user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(request
                        .getRole()))
                .build();

        userRepository.save(user);

        var token = jwtService.generateToken(user);

        return UserAuthResponse.builder().accessToken(token).build();

    }

    @Override
    public UserAuthResponse authenticate(AuthenticateRequest request) {
        // Attempt authentication
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            log.error("Authentication failed for email: {}", request.getEmail(), e);
            throw new CustomBadCredentialsException("Authentication failed, check yur email or pasuuad");
        }

        // Retrieve user details
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Generate token
        var token = jwtService.generateToken(user);

        return UserAuthResponse.builder().accessToken(token).build();
    }
}
