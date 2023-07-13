package com.example.rest_practice.Mapper;

import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Model.Bike;

public interface BikeMapper {
    BikeDTO convertToDTO(Bike bike);
    Bike convertToBike(BikeDTO dto);
}
