package com.joe.springsec6demo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_CREATE("Admin:create"),
    ADMIN_READ("Admin:read"),
    ADMIN_UPDATE("Admin:update"),
    ADMIN_DELETE("Admin:delete"),
    MEMBER_CREATE("member:create"),
    MEMBER_READ("member:read"),
    MEMBER_UPDATE("member:update"),
    MEMBER_DELETE("member:delete"),;

    private final String permission;


}
