package com.example.carservice.exceptions.data;

public class IncorrectDateTimeException extends RuntimeException{
    public IncorrectDateTimeException(String message){
        super(message);
    }
}
