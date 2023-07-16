package com.example.rest_practice.Controller;

import com.example.rest_practice.DTO.Request.LoginRequest;
import com.example.rest_practice.DTO.Response.LoginResponse;
import com.example.rest_practice.DTO.Response.RegistrationResponse;
import com.example.rest_practice.Exception.DuplicateUserException;
import com.example.rest_practice.Model.Customer;
import com.example.rest_practice.Service.CustomerService;
import com.example.rest_practice.Service.LoginService;
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
    private final LoginService loginService;
    @Autowired
    public AuthController(CustomerService customerService, LoginService loginService) {
        this.customerService = customerService;
        this.loginService = loginService;
    }


    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) throws Exception {
        System.out.println(request.getEmail() + " " + request.getPassword());
        return LoginResponse.builder()
                .token(loginService.loginProcessor(request))
                .build();
    }

    @PostMapping("/registration")
    public RegistrationResponse registrationCustomer(@RequestBody @Valid Customer customer) throws DuplicateUserException {
        String token = customerService.createNewCustomer(customer);
        return RegistrationResponse.builder()
                .token(token)
                .build();
    }
}
