package com.example.rest_practice.Mapper.Impl;

import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Mapper.BikeMapper;
import com.example.rest_practice.Model.Bike;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BikeMapperImpl implements BikeMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public BikeMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BikeDTO convertToDTO(Bike bike) {
        return modelMapper.map(bike, BikeDTO.class);
    }

    public Bike convertToBike(BikeDTO dto) {
        return modelMapper.map(dto, Bike.class);
    }
}
