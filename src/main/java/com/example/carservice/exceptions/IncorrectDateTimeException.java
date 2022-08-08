package com.example.carservice.exceptions;

public class IncorrectDateTimeException extends RuntimeException{
    public IncorrectDateTimeException(String message){
        super(message);
    }
}
