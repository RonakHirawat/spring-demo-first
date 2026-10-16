package com.example.demomanager.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(TaskNotFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateTitleException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateTitleException ex) {
        return ResponseEntity.status(409).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalid(MethodArgumentNotValidException ex) {
        String message = "";
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            message = message + error.getField() + ": " + error.getDefaultMessage() + "; ";
        }
        return ResponseEntity.status(400).body(new ErrorResponse(message));
    }
}