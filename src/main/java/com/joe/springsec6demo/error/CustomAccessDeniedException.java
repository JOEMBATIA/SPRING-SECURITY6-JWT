package com.joe.springsec6demo.error;

import java.nio.file.AccessDeniedException;

public class CustomAccessDeniedException extends AccessDeniedException {
    public CustomAccessDeniedException(String file) {
        super(file);
    }

    public CustomAccessDeniedException(String file, String other, String reason) {
        super(file, other, reason);
    }
}
