package com.example.rest_practice.Service;

import com.example.rest_practice.Exception.DuplicateUserException;
import com.example.rest_practice.Model.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findByUsername(String username);
    String createNewCustomer(Customer customer) throws DuplicateUserException;
}
