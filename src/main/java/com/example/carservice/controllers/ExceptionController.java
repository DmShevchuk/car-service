package com.example.carservice.controllers;

import com.example.carservice.entities.exception.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionController {
 //   @ExceptionHandler(Exception.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
   // public ApiError handleException(Exception e) {
  //      return new ApiError(e.getClass().getName(), e.getMessage());
  //  }
}
