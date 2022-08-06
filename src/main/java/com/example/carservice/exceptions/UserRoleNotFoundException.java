package com.example.carservice.exceptions;

public class UserRoleNotFoundException extends RuntimeException{
    public UserRoleNotFoundException(String roleName) {
        super(String.format("Role with name '%s' does not exist!", roleName));
    }
}