package com.example.adventurebot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoHandlerFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Not Found");
        body.put("message", "The requested endpoint does not exist");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Method Not Allowed");
        body.put("message", "HTTP method not supported for this endpoint");
        return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
