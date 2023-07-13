package com.example.rest_practice.Controller;

import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Mapper.BikeMapper;
import com.example.rest_practice.Service.BikeService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
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
        return new ResponseEntity<>(bikeService.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public BikeDTO findBikeById(@PathVariable("id") Long id) {
        return bikeService.findBikeById(id);
    }

    @PostMapping("/")
    public ResponseEntity saveBike(@RequestBody BikeDTO bikeDTO, Principal principal) throws AccessDeniedException {
        if(principal == null){
            throw new AccessDeniedException("Invalid principal data");
        }
        bikeService.saveBike(bikeMapper.convertToBike(bikeDTO), principal);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping ("/{id}")
    public ResponseEntity updateBike(@PathVariable("id") Long id, @RequestBody BikeDTO updatedBikeDTO, Principal principal) throws AccessDeniedException {
        if(principal==null){
            throw new AccessDeniedException("Invalid principal data");
        }
        bikeService.updateBikeInfo(id, updatedBikeDTO, principal);
        return ResponseEntity.ok().build();
    }
}
