package com.example.rest_practice.Exception;

public class DuplicateUserException extends Exception{
    public DuplicateUserException(String message) {
        super(message);
    }
}
