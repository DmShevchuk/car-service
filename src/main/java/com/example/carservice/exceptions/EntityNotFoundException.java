package com.example.carservice.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String className, Long id) {
        super(String.format("%s with id=%s does not exist!", className, id));
    }
}