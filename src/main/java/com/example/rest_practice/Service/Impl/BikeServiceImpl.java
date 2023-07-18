package com.example.rest_practice.Service.Impl;

import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Mapper.BikeMapper;
import com.example.rest_practice.Model.Bike;
import com.example.rest_practice.Model.RentStatus;
import com.example.rest_practice.Repository.BikeRepository;
import com.example.rest_practice.Repository.CustomerRepository;
import com.example.rest_practice.Service.BikeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
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
    public List<BikeDTO> findAllAvailableBikes() {
        return bikeRepository.findAll()
                .stream()
                .filter(bike -> bike.getStatus().equals(RentStatus.AVAILABLE))
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BikeDTO findBikeById(Long id) {
        Optional<Bike> b = bikeRepository.findById(id);
        if (b.isPresent()) {
            return mapper.convertToDTO(b.get());
        } else {
            throw new EntityNotFoundException("No such bike with id " + id);
        }
    }

    @Override
    public void saveBike(Bike bike, UserDetails userDetails) throws AccessDeniedException {
        Optional<Bike> b = bikeRepository.findBikeBySerialNumber(bike.getSerialNumber());
        if (b.isEmpty()) {
            bike.setStatus(RentStatus.AVAILABLE);
            bike.setCustomer(customerRepository.findByUsername(userDetails.getUsername()).get());
            bikeRepository.saveAndFlush(bike);
            log.info("Successfully saved new bike " + mapper.convertToDTO(bike) + " by customer " + userDetails.getUsername());
        } else if (b.get().getStatus().equals(RentStatus.UNAVAILABLE)) {
            Bike existingBike = bikeRepository.findBikeBySerialNumber(bike.getSerialNumber()).get();
            bike.setStatus(RentStatus.AVAILABLE);
            updateBikeInfo(existingBike.getId(), mapper.convertToDTO(bike), userDetails);
            log.info("User " + userDetails.getUsername() + " successfully update status to " + bike.getStatus() + " for bike " + bike.getSerialNumber());
        } else {
            log.info("User " + userDetails.getUsername() + " tried to save exist bike " + b.get().getSerialNumber());
            throw new AccessDeniedException("Bike with S/N: " + b.get().getSerialNumber() + " already exists");
        }
    }

    @Override
    public Bike findById(Long id) {
        Optional<Bike> b = bikeRepository.findById(id);
        if(b.isPresent()) {
            log.info("Successfully found bike with id " + b.get().getId());
            return b.get();
        } else{
            log.info("No such bike with id " + id);
            throw new EntityNotFoundException("No such bike with id " + id);
        }
    }

    @Override
    public void updateBikeInfo(Long id, BikeDTO updatedBikeDTO, UserDetails userDetails) throws AccessDeniedException {
        Bike existingBike = findById(id);

        if (existingBike == null) {
            log.info("Incorrect bike id " + id);
            throw new IllegalArgumentException("Bike data is required");
        }
        if (!userDetails.getUsername().equals(existingBike.getCustomer().getUsername())) {
            log.info("User " + userDetails.getUsername() + " tries to get access to bike " + existingBike.getSerialNumber());
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
        bikeRepository.save(existingBike);
        log.info("User with username " + userDetails.getUsername() + " successfully changed information about " + mapper.convertToDTO(existingBike));
    }

    @Override
    public void deleteBikeById(Long id, UserDetails userDetails) throws AccessDeniedException {
        Optional<Bike> b = bikeRepository.findById(id);
        if (b.isEmpty()) {
            log.info("Bike with id " + id + " isn't presented");
            throw new IllegalArgumentException("Bike with id " + id + " isn't presented");
        }
        if (bikeRepository.findById(id).get().getCustomer().getUsername().equals(userDetails.getUsername())) {
            log.info("Bike with S/N " + b.get().getSerialNumber() + " now Unavailable to rental");
            bikeRepository.setUnavailableStatusBySerialNumber(b.get().getSerialNumber());
        } else {
            throw new AccessDeniedException("This isn't your bike");
        }
    }

    @Override
    public Bike findBikeBySerialNumber(String serialNumber) {
        Optional<Bike> b = bikeRepository.findBikeBySerialNumber(serialNumber);
        if (b.isPresent()) {
            return b.get();
        } else {
            throw new EntityNotFoundException("No su bike with S/N" + serialNumber);
        }
    }
}
