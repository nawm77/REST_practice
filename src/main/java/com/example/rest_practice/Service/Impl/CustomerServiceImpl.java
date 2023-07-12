package com.example.rest_practice.Service.Impl;

import com.example.rest_practice.Model.Customer;
import com.example.rest_practice.Repository.CustomerRepository;
import com.example.rest_practice.Repository.RoleRepository;
import com.example.rest_practice.Service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements UserDetailsService, CustomerService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, RoleRepository roleRepository) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
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

    public void createNewCustomer(Customer customer){
        customer.setRole(List.of(roleRepository.findByName("ROLE_USER").get()));
        customerRepository.saveAndFlush(customer);
    }
}
