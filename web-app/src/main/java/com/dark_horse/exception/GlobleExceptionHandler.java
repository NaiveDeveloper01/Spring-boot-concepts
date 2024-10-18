package com.dark_horse.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handle(Exception e) {
        return "Exceeded";
    }
}
