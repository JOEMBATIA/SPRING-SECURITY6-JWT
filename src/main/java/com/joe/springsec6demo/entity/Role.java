package com.joe.springsec6demo.entity;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

@Getter
public enum Role {
    ADMIN(
            Set.of(
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_DELETE,
                    Permission.MEMBER_CREATE,
                    Permission.MEMBER_READ,
                    Permission.MEMBER_UPDATE,
                    Permission.MEMBER_DELETE
                    )),

    MEMBER(
            Set.of(
                    Permission.MEMBER_CREATE,
                    Permission.MEMBER_READ,
                    Permission.MEMBER_UPDATE
                    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = new java.util.ArrayList<>(getPermissions()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(
                        authority.getPermission()
                ))
                .toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}
