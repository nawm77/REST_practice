package com.example.rest_practice.Controller;

import com.example.rest_practice.Model.Customer;
import com.example.rest_practice.Model.LoginRequest;
import com.example.rest_practice.Model.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request){
        return LoginResponse.builder()
                .accessToken("smthng")
                .build();
    }

    @PostMapping("/registration")
    public ResponseEntity registrationCustomer(@RequestBody @Validated Customer customer){

    }
}
