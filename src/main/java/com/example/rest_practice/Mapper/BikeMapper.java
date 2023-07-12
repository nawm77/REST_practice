package com.example.rest_practice.Mapper;

import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Model.Bike;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BikeMapper {
    BikeMapper INSTANCE = Mappers.getMapper(BikeMapper.class);
    BikeDTO toDTO(Bike bike);
}
