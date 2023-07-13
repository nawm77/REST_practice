package com.example.rest_practice.Service;

import com.example.rest_practice.DTO.Request.LoginRequest;
import com.example.rest_practice.Exception.DuplicateUserException;
import com.example.rest_practice.Exception.UserNotFoundException;
import com.example.rest_practice.Model.Customer;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findByUsername(String username);
    String createNewCustomer(Customer customer) throws DuplicateUserException;
    String generateToken(UserDetails userDetails);
    Customer findCustomerByEmail(String email) throws UserNotFoundException;
    Boolean loginProcessor(LoginRequest loginRequest) throws UserNotFoundException;
    UserDetails loadUserByUsername(String username);
}
