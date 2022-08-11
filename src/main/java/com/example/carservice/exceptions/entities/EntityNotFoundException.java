package com.example.carservice.exceptions.entities;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String className, Long id) {
        super(String.format("%s with id=%s does not exist!", className, id));
    }

    public EntityNotFoundException(String className, String field) {
        super(String.format("%s with '%s' does not exist!", className, field));
    }
}