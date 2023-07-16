package com.example.rest_practice.Service.Impl;

import com.example.rest_practice.DTO.CustomerDTO;
import com.example.rest_practice.Exception.DuplicateUserException;
import com.example.rest_practice.Exception.UserNotFoundException;
import com.example.rest_practice.Mapper.CustomerMapper;
import com.example.rest_practice.Model.Customer;
import com.example.rest_practice.Model.Role;
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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@Log4j2
public class CustomerServiceImpl implements UserDetailsService, CustomerService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, RoleRepository roleRepository, JwtService jwtService, PasswordEncoder passwordEncoder, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.customerMapper = customerMapper;
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
    public List<CustomerDTO> findAll() {
        return customerRepository.findAll().stream().map(customerMapper::convertToDTO).toList();
    }

    @Override
    public Customer findById(Long id) throws NoSuchElementException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User wasn't found"));
    }

    @Override
    public void blockCustomerById(Long id) {
        customerRepository.blockCustomerById(id);
    }

    @Override
    public void unblockCustomerById(Long id) {
        customerRepository.unblockCustomerById(id);
    }

    @Transactional
    @Override
    public void setCustomerRole(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("No such user"));
        Customer fin = new Customer();
        fin.setRole(Set.of(roleRepository.findById(2).get()));
        customer.getRole().addAll(
                fin
                        .getRole().stream().map(r -> {
                            Role rr = roleRepository.findById(r.getId()).get();
                            rr.getCustomer().add(customer);
                            return rr;
                        }).toList()
        );
        customerRepository.save(customer);
    }

    @Override
    public String createNewCustomer(Customer customer) throws DuplicateUserException{
        if(customerRepository.findAllByUsername(customer.getUsername()).size()!=0 || customerRepository.findByEmail(customer.getEmail()).isPresent()){
            throw new DuplicateUserException("User with email: " + customer.getEmail() +" already exists in database");
        }
        customer.setRole(Set.of(roleRepository.findById(1).get()));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setIsNonBlocked(true);
        customerRepository.saveAndFlush(customer);
        return jwtService.generateToken(customer);
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
}
