package com.example.rest_practice.Controller;

import com.example.rest_practice.DTO.CustomerDTO;
import com.example.rest_practice.Mapper.CustomerMapper;
import com.example.rest_practice.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping("/list")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") Long id) throws NoSuchElementException {
        return ResponseEntity.ok(customerMapper.convertToDTO(customerService.findById(id)));
    }

    @PostMapping("/block/{id}")
    public ResponseEntity<?> blockCustomerById(@PathVariable("id") Long id) {
        customerService.blockCustomerById(id);
        return ResponseEntity.status(204).build();
    }

    @PostMapping("/unblock/{id}")
    public ResponseEntity<?> unblockCustomerById(@PathVariable("id") Long id) {
        customerService.unblockCustomerById(id);
        return ResponseEntity.status(204).build();
    }
}
