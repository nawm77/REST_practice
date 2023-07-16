package com.example.rest_practice.Mapper;

import com.example.rest_practice.DTO.RentRequestDTO;
import com.example.rest_practice.Model.RentRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface RentRequestMapper {
    RentRequest toRentRequest(RentRequestDTO dto, UserDetails userDetails);
    RentRequestDTO toDTO(RentRequest rentRequest, UserDetails userDetails);
}
