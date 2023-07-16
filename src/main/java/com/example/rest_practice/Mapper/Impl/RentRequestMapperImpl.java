package com.example.rest_practice.Mapper.Impl;

import com.example.rest_practice.DTO.RentRequestDTO;
import com.example.rest_practice.Mapper.RentRequestMapper;
import com.example.rest_practice.Model.Customer;
import com.example.rest_practice.Model.RentRequest;
import com.example.rest_practice.Service.BikeService;
import com.example.rest_practice.Service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class RentRequestMapperImpl implements RentRequestMapper {
    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final BikeService bikeService;

    @Autowired
    public RentRequestMapperImpl(ModelMapper modelMapper, CustomerService customerService, BikeService bikeService) {
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.bikeService = bikeService;
    }

    @Override
    public RentRequest toRentRequest(RentRequestDTO dto, UserDetails userDetails) {
        RentRequest req = modelMapper.map(dto, RentRequest.class);
        req.setBike(bikeService.findBikeBySerialNumber(dto.getBikeSerialNumber()));
        req.setRenter(customerService.findByUsername(userDetails.getUsername()).get());
        return req;
    }

    @Override
    public RentRequestDTO toDTO(RentRequest rentRequest, UserDetails userDetails) {
        RentRequestDTO dto = modelMapper.map(rentRequest, RentRequestDTO.class);
        Customer c = customerService.findByUsername(userDetails.getUsername()).get();
        dto.setCustomerName(c.getName());
        dto.setCustomerSurname(c.getSurname());
        return dto;
    }
}
