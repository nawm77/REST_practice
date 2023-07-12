package com.example.rest_practice.Service;

import com.example.rest_practice.Model.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CustomerService {
    Optional<Customer> findByUsername(String username);
    void createNewCustomer(Customer customer);
}
