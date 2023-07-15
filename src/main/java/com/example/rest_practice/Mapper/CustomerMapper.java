package com.example.rest_practice.Mapper;

import com.example.rest_practice.DTO.CustomerDTO;
import com.example.rest_practice.Model.Customer;

public interface CustomerMapper {
    CustomerDTO convertToDTO(Customer customer);
    Customer convertToCustomer(CustomerDTO customerDTO);
}
