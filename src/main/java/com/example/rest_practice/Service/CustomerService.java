package com.example.rest_practice.Service;

import com.example.rest_practice.DTO.CustomerDTO;
import com.example.rest_practice.Exception.DuplicateUserException;
import com.example.rest_practice.Exception.UserNotFoundException;
import com.example.rest_practice.Model.Customer;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findByUsername(String username);
    String createNewCustomer(Customer customer) throws DuplicateUserException;
    Customer findCustomerByEmail(String email) throws UserNotFoundException;
    UserDetails loadUserByUsername(String username);
    List<CustomerDTO> findAll();
    Customer findById(Long id) throws NoSuchElementException;
    void blockCustomerById(Long id);
    void unblockCustomerById(Long id);
    void setCustomerRole(Long id);
}
