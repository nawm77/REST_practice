package com.example.rest_practice.Mapper.Impl;

import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Mapper.BikeMapper;
import com.example.rest_practice.Model.Bike;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BikeMapperImpl implements BikeMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public BikeMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BikeDTO convertToDTO(Bike bike){
        PropertyMap<Bike, BikeDTO>bikeToBikeDTO = new PropertyMap<Bike, BikeDTO>() {
            @Override
            protected void configure() {
                map().setMake(source.getMake());
                map().setModel(source.getModel());
                map().setSerialNumber(source.getSerialNumber());
                map().setCostPerHour(source.getCostPerHour());
                map().setCostPerDay(source.getCostPerDay());
                map().setStatus(String.valueOf(source.getStatus()));
                map().setCustomerId(source.getCustomer().getId());
                map().setCustomerName(source.getCustomer().getName());
                map().setCustomerLastname(source.getCustomer().getSurname());
                map().setCustomerEmail(source.getCustomer().getEmail());
            }
        };
        return modelMapper.addMappings(bikeToBikeDTO).map(bike);
    }

    public Bike convertToBike(BikeDTO dto){
        return modelMapper.map(dto, Bike.class);
    }
}
