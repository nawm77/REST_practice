package com.example.rest_practice;

public class DuplicateUserException extends Exception{
    public DuplicateUserException(String message) {
        super(message);
    }
}
