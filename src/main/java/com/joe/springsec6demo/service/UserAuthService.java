package com.joe.springsec6demo.service;

import com.joe.springsec6demo.dto.AuthenticateRequest;
import com.joe.springsec6demo.dto.UserAuthResponse;
import com.joe.springsec6demo.dto.UserRequest;

public interface UserAuthService {
    UserAuthResponse register(UserRequest request);

    UserAuthResponse authenticate(AuthenticateRequest request);
}
