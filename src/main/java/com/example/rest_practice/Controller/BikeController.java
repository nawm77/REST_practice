package com.example.rest_practice.Controller;

import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Mapper.BikeMapper;
import com.example.rest_practice.Service.BikeService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {
    private final BikeService bikeService;
    private final BikeMapper bikeMapper;

    @Autowired
    public BikeController(BikeService bikeService, BikeMapper bikeMapper) {
        this.bikeService = bikeService;
        this.bikeMapper = bikeMapper;
    }

    @PermitAll
    @GetMapping("/list")
    public ResponseEntity<List<BikeDTO>> getAllBikes() {
        return new ResponseEntity<>(bikeService.findAllAvailableBikes(), HttpStatus.FOUND);
    }

    @PermitAll
    @GetMapping("/list/{id}")
    public ResponseEntity<BikeDTO> findBikeById(@PathVariable("id") Long id) {
        return ResponseEntity.status(200).body(bikeService.findBikeById(id));
    }
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> saveBike(@RequestBody BikeDTO bikeDTO, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        bikeService.saveBike(bikeMapper.convertToBike(bikeDTO), userDetails);
        return ResponseEntity.status(201).build();
    }
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @PutMapping ("/edit/{id}")
    public ResponseEntity<?> updateBike(@PathVariable("id") Long id, @RequestBody BikeDTO updatedBikeDTO, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        bikeService.updateBikeInfo(id, updatedBikeDTO, userDetails);
        return ResponseEntity.status(204).build();
    }
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBikeById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        bikeService.deleteBikeById(id, userDetails);
        return ResponseEntity.status(204).build();
    }
}
