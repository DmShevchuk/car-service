package com.example.carservice.exceptions;

public class BoxAlreadyExistsException extends RuntimeException {
    public BoxAlreadyExistsException(String name) {
        super(String.format("Box with name '%s' already exists!", name));
    }
}
