package com.example.rest_practice.Controller;

import com.example.rest_practice.DTO.Request.LoginRequest;
import com.example.rest_practice.DTO.Response.LoginResponse;
import com.example.rest_practice.DTO.Response.RegistrationResponse;
import com.example.rest_practice.Exception.DuplicateUserException;
import com.example.rest_practice.Model.Customer;
import com.example.rest_practice.Service.CustomerService;
import com.example.rest_practice.Service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication controller")
public class AuthController {
    private final CustomerService customerService;
    private final LoginService loginService;

    @Autowired
    public AuthController(CustomerService customerService, LoginService loginService) {
        this.customerService = customerService;
        this.loginService = loginService;
    }

    @Operation(summary = "Login exists user", description = "Метод принимает LoginRequest для аутентификации пользователя и выдает токен")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws Exception {
        System.out.println("nn");
        return ResponseEntity.status(200).body(LoginResponse.builder()
                .token(loginService.loginProcessor(request))
                .build());
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> registrationCustomer(@RequestBody @Valid Customer customer) throws DuplicateUserException {
        String token = customerService.createNewCustomer(customer);
        return ResponseEntity.status(200).body(RegistrationResponse.builder()
                .token(token)
                .build());
    }
}
