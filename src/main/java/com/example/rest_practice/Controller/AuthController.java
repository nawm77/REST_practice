package com.example.rest_practice.Controller;

import com.example.rest_practice.DuplicateUserException;
import com.example.rest_practice.Model.Customer;
import com.example.rest_practice.Model.LoginRequest;
import com.example.rest_practice.Model.LoginResponse;
import com.example.rest_practice.Service.CustomerService;
import com.example.rest_practice.Service.VerificationTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final CustomerService customerService;
    private final VerificationTokenService verificationTokenService;

    @Autowired
    public AuthController(CustomerService customerService, VerificationTokenService verificationTokenService) {
        this.customerService = customerService;
        this.verificationTokenService = verificationTokenService;
    }


    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return LoginResponse.builder()
                .accessToken("smthng")
                .build();
    }

    @PostMapping("/registration")
    public String registrationCustomer(@RequestBody @Valid Customer customer) throws DuplicateUserException {
        String token = customerService.createNewCustomer(customer);
        return token;
    }
}
