package com.joe.springsec6demo.controller;

import com.joe.springsec6demo.dto.AuthenticateRequest;
import com.joe.springsec6demo.dto.UserAuthResponse;
import com.joe.springsec6demo.dto.UserRequest;
import com.joe.springsec6demo.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final UserAuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserAuthResponse> registerUser(@RequestBody UserRequest request){
        UserAuthResponse authResponse = authService.register(request);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthResponse> authenticate(@RequestBody AuthenticateRequest request){
        UserAuthResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}
