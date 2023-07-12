package com.example.rest_practice.Service.Impl;

import com.example.rest_practice.Model.Bike;
import com.example.rest_practice.Model.RentStatus;
import com.example.rest_practice.Repository.BikeRepository;
import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Mapper.BikeMapper;
import com.example.rest_practice.Service.BikeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BikeServiceImpl implements BikeService {
    private final BikeRepository bikeRepository;

    @Autowired
    public BikeServiceImpl(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public List<BikeDTO> findAll() {
        StopWatch sw = new StopWatch();
        sw.start();
        List<BikeDTO> list = bikeRepository.findAll()
                .stream()
                .map(BikeMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
        sw.stop();
        log.info("BikeService method findAll() executed in {} ms. Found {} items", sw.getTotalTimeMillis(), list.size());
        return list;
    }

    public BikeDTO findBikeById(Long id) {
        return BikeMapper.INSTANCE.toDTO(
                bikeRepository.findById(id).get()
        );
    }

    public void saveBike(Bike bike) {
        bike.setStatus(RentStatus.AVAILABLE);
        bikeRepository.saveAndFlush(bike);
        log.info("Successfully saved new bike " + BikeMapper.INSTANCE.toDTO(bike));
    }

    @Override
    public Bike findById(Long id) {
        return bikeRepository.findById(id).get();
    }
}
