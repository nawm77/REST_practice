package com.example.rest_practice.Service;

import com.example.rest_practice.DTO.RentRequestDTO;
import com.example.rest_practice.DTO.Response.RentStopResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

public interface RentService {
    List<RentRequestDTO> findAllByCustomer(UserDetails userDetails);
    void startRent(RentRequestDTO rentRequestDTO, UserDetails userDetails) throws Exception;
    void deleteById(Long id, UserDetails userDetails) throws AccessDeniedException;
    RentStopResponse stopRentById(Long id, UserDetails userDetails, LocalDateTime time) throws AccessDeniedException;
}
