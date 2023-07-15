package com.example.rest_practice.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFoundException(Exception ex) {
        HttpStatus serverError = UNAUTHORIZED;
        ApiException apiException = new ApiException(ex.getMessage(), serverError);
        return new ResponseEntity<>(apiException, serverError);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unexpectedException(Exception ex) {
        HttpStatus serverError = INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(ex.getMessage(), serverError);
        return new ResponseEntity<>(apiException, serverError);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
        String errorMessage = "JWT expired at " + ex.getClaims().getExpiration();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorMessage);
    }
}