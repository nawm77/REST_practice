package com.example.rest_practice.Service.Impl;

import com.example.rest_practice.Exception.DuplicateUserException;
import com.example.rest_practice.Model.Customer;
import com.example.rest_practice.Repository.CustomerRepository;
import com.example.rest_practice.Repository.RoleRepository;
import com.example.rest_practice.Service.CustomerService;
import com.example.rest_practice.Service.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements UserDetailsService, CustomerService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, RoleRepository roleRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }
    public Optional<Customer> findByUsername(String username){
        return customerRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Username '%s' isn't presented", username)));
        return new User(customer.getUsername(),
                customer.getPassword(),
                customer.getRole()
                        .stream()
                        .map(role ->  new SimpleGrantedAuthority(role.getName()))
                        .toList());
    }
    @Override
    public String createNewCustomer(Customer customer) throws DuplicateUserException{
        if(customerRepository.findAllByUsername(customer.getUsername()).size()!=0){
            throw new DuplicateUserException("User with email: " + customer.getEmail() +" already exists in database");
        }
        customer.setRole(List.of(roleRepository.findById(1).get()));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.saveAndFlush(customer);
        return jwtService.generateToken(customer);
    }
}
