package com.example.rest_practice.Mapper;

import com.example.rest_practice.DTO.CustomerDTO;
import com.example.rest_practice.Model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    CustomerDTO toDTO(Customer customer);
}
