package com.example.rest_practice.Service;

import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Model.Bike;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface BikeService {
    List<BikeDTO> findAllAvailableBikes();
    BikeDTO findBikeById(Long id) throws EntityNotFoundException;
    void saveBike(Bike bike, UserDetails userDetails) throws AccessDeniedException;
    Bike findById(Long id);
    void updateBikeInfo(Long id, BikeDTO dto, UserDetails userDetails) throws AccessDeniedException;
    void deleteBikeById(Long id, UserDetails userDetails) throws AccessDeniedException;
    Bike findBikeBySerialNumber(String serialNumber);
}
