package com.example.rest_practice.Service;

import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Model.Bike;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public interface BikeService {
    List<BikeDTO> findAll();
    BikeDTO findBikeById(Long id);
    void saveBike(Bike bike, UserDetails userDetails) throws AccessDeniedException;
    Bike findById(Long id);
    void updateBikeInfo(Long id, BikeDTO dto, UserDetails userDetails) throws AccessDeniedException;
    void deleteBikeById(Long id, UserDetails userDetails) throws AccessDeniedException;
    Bike findBikeBySerialNumber(String serialNumber);
}
