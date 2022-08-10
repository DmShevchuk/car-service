package com.example.carservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER"),
    ROLE_OPERATOR("ROLE_OPERATOR");

    private final String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
