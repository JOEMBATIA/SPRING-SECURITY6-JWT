package com.joe.springsec6demo.error;

import com.joe.springsec6demo.entity.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomBadCredentialsException.class)
    public ResponseEntity<ErrorMessage> invalidCredentials(
            BadCredentialsException exception, WebRequest request
    ){
        ErrorMessage message = ErrorMessage.builder()
                .message(exception.getMessage())
                .status(HttpStatus.FORBIDDEN)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(message);

    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDenied(
            CustomAccessDeniedException exception, WebRequest request
    ){
        ErrorMessage message = ErrorMessage.builder()
                .message(exception.getMessage())
                .status(HttpStatus.FORBIDDEN)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(message);
    }
 }
