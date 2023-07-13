package com.example.rest_practice.Service;

import com.example.rest_practice.DTO.Request.LoginRequest;

public interface LoginService {
    String loginProcessor(LoginRequest loginRequest) throws Exception;
}
