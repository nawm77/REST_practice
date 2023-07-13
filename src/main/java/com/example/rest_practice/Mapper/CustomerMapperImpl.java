package com.example.rest_practice.Mapper;

import com.example.rest_practice.DTO.CustomerDTO;
import com.example.rest_practice.Model.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapperImpl {
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerDTO convertToDTO(Customer customer){
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public Customer convertToCustomer(CustomerDTO dto){
        return modelMapper.map(dto, Customer.class);
    }
}
