package com.example.rest_practice.Service.Impl;

import com.example.rest_practice.DTO.Request.LoginRequest;
import com.example.rest_practice.Exception.DuplicateUserException;
import com.example.rest_practice.Exception.UserNotFoundException;
import com.example.rest_practice.Model.Customer;
import com.example.rest_practice.Repository.CustomerRepository;
import com.example.rest_practice.Repository.RoleRepository;
import com.example.rest_practice.Service.CustomerService;
import com.example.rest_practice.Service.JwtService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
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
        System.out.println(customer.getRole());
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
    @Override
    public String generateToken(UserDetails userDetails){
        log.info("Generated new token for user " + userDetails.getUsername());
        return jwtService.generateToken(userDetails);
    }

    @Override
    public Customer findCustomerByEmail(String email) throws UserNotFoundException {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if(customer.isPresent()){
            return customer.get();
        } else{
            log.info("User with email " + email + " wasn't found");
            throw new UserNotFoundException("User with email " + email + " wasn't found");
        }
    }

    @Override
    public Boolean loginProcessor(LoginRequest loginRequest) throws UserNotFoundException {
        return findCustomerByEmail(loginRequest.getEmail()).getPassword().equals(passwordEncoder.encode(loginRequest.getPassword()));
    }


}
