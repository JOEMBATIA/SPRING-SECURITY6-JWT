package com.joe.springsec6demo.error;

import org.springframework.security.authentication.BadCredentialsException;

public class CustomBadCredentialsException extends BadCredentialsException {
    public CustomBadCredentialsException(String msg) {
        super(msg);
    }

    public CustomBadCredentialsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
