package com.example.rest_practice.Service;

import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Model.Bike;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@Service
public interface BikeService {
    List<BikeDTO> findAll();
    BikeDTO findBikeById(Long id);
    void saveBike(Bike bike, Principal principal) throws AccessDeniedException;
    Bike findById(Long id);
    void updateBikeInfo(Long id, BikeDTO dto, Principal principal) throws AccessDeniedException;
}
