package com.example.carservice.exceptions.auth;

public class ConfirmationTokenExpireException extends RuntimeException{
    public ConfirmationTokenExpireException(String token) {
        super(String.format("Confirmation token '%s' expired!", token));
    }
}
