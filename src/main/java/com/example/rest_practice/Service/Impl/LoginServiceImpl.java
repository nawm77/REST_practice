package com.example.rest_practice.Service.Impl;

import com.example.rest_practice.DTO.Request.LoginRequest;
import com.example.rest_practice.Model.Customer;
import com.example.rest_practice.Service.CustomerService;
import com.example.rest_practice.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements com.example.rest_practice.Service.LoginService {
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public LoginServiceImpl(CustomerService customerService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String loginProcessor(LoginRequest loginRequest) throws Exception {
        Customer customer = customerService.findCustomerByEmail(loginRequest.getEmail());
        if(passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())) {
            return jwtService.generateToken(customer);
        } else{
            throw new IllegalArgumentException("Invalid password");
        }
    }
}
