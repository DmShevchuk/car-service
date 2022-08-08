package com.example.carservice.exceptions;

public class ConfirmationNotFoundException extends RuntimeException{
    public ConfirmationNotFoundException(String token) {
        super(String.format("Confirmation with token '%s' does not exist!", token));
    }
}