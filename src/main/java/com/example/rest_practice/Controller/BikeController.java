package com.example.rest_practice.Controller;

import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Model.Bike;
import com.example.rest_practice.Model.RentStatus;
import com.example.rest_practice.Service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {
    private final BikeService bikeService;

    @Autowired
    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<BikeDTO>> getAllBikes() {
        return new ResponseEntity<>(bikeService.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public BikeDTO findBikeById(@PathVariable("id") Long id) {
        return bikeService.findBikeById(id);
    }

    @PostMapping("/")
    public ResponseEntity saveBike(@RequestBody Bike bike) {
        bikeService.saveBike(bike);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateBike(@PathVariable("id") Long id, @RequestBody BikeDTO updatedBikeDTO) {
        Bike existingBike = bikeService.findById(id);

        if (existingBike == null) {
            return ResponseEntity.notFound().build();
        }

        if (updatedBikeDTO.make() != null) {
            existingBike.setMake(updatedBikeDTO.make());
        }
        if (updatedBikeDTO.model() != null) {
            existingBike.setModel(updatedBikeDTO.model());
        }
        if (updatedBikeDTO.serialNumber() != null) {
            existingBike.setSerialNumber(updatedBikeDTO.serialNumber());
        }
        if (updatedBikeDTO.costPerHour() != null) {
            existingBike.setCostPerHour(updatedBikeDTO.costPerHour());
        }
        if (updatedBikeDTO.costPerDay() != null) {
            existingBike.setCostPerDay(updatedBikeDTO.costPerDay());
        }
        if (updatedBikeDTO.status() != null) {
            existingBike.setStatus(RentStatus.valueOf(updatedBikeDTO.status()));
        }
        bikeService.saveBike(existingBike);
        return ResponseEntity.ok().build();
    }
}
