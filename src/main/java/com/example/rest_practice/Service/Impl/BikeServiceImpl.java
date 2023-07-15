package com.example.rest_practice.Service.Impl;

import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Mapper.BikeMapper;
import com.example.rest_practice.Model.Bike;
import com.example.rest_practice.Model.RentStatus;
import com.example.rest_practice.Repository.BikeRepository;
import com.example.rest_practice.Repository.CustomerRepository;
import com.example.rest_practice.Service.BikeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BikeServiceImpl implements BikeService {
    private final BikeRepository bikeRepository;
    private final BikeMapper mapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public BikeServiceImpl(BikeRepository bikeRepository, BikeMapper mapper, CustomerRepository customerRepository) {
        this.bikeRepository = bikeRepository;
        this.mapper = mapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<BikeDTO> findAll() {
        return bikeRepository.findAll()
                .stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BikeDTO findBikeById(Long id) {
        return mapper.convertToDTO(
                bikeRepository.findById(id).get()
        );
    }

    @Override
    public void saveBike(Bike bike, UserDetails userDetails){
        bike.setStatus(RentStatus.AVAILABLE);
        bike.setCustomer(customerRepository.findByUsername(userDetails.getUsername()).get());
        bikeRepository.saveAndFlush(bike);
        log.info("Successfully saved new bike " + mapper.convertToDTO(bike) + " by customer " + userDetails.getUsername());
    }

    @Override
    public Bike findById(Long id) {
        return bikeRepository.findById(id).get();
    }

    @Override
    public void updateBikeInfo(Long id, BikeDTO updatedBikeDTO, UserDetails userDetails) throws AccessDeniedException {
        Bike existingBike = findById(id);

        if (existingBike == null) {
            throw new IllegalArgumentException("Bike data is required");
        }
        if (!userDetails.getUsername().equals(existingBike.getCustomer().getUsername()) ){
            throw new AccessDeniedException("You are not owner of bike with S/N " + updatedBikeDTO.getSerialNumber());
        }

        if (updatedBikeDTO.getMake() != null) {
            existingBike.setMake(updatedBikeDTO.getMake());
        }
        if (updatedBikeDTO.getModel() != null) {
            existingBike.setModel(updatedBikeDTO.getModel());
        }
        if (updatedBikeDTO.getSerialNumber() != null) {
            existingBike.setSerialNumber(updatedBikeDTO.getSerialNumber());
        }
        if (updatedBikeDTO.getCostPerHour() != null) {
            existingBike.setCostPerHour(updatedBikeDTO.getCostPerHour());
        }
        if (updatedBikeDTO.getCostPerDay() != null) {
            existingBike.setCostPerDay(updatedBikeDTO.getCostPerDay());
        }
        if (updatedBikeDTO.getStatus() != null) {
            existingBike.setStatus(RentStatus.valueOf(updatedBikeDTO.getStatus()));
        }
        saveBike(existingBike, userDetails);
        log.info("User with username " + userDetails.getUsername() + " successfully changed information about " + mapper.convertToDTO(existingBike));
    }

    @Override
    public void deleteBikeById(Long id, UserDetails userDetails) throws AccessDeniedException {
        if(!bikeRepository.findById(id).isPresent()){
            throw new IllegalArgumentException("Bike with id " + id + " isn't presented");
        }
        if(bikeRepository.findById(id).get().getCustomer().getUsername().equals(userDetails.getUsername())){
            bikeRepository.deleteById(id);
        }else{
            throw new AccessDeniedException("This isn't your bike");
        }
    }
}
