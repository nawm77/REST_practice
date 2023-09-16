package com.example.rest_practice.Controller;

import com.example.rest_practice.DTO.BikeDTO;
import com.example.rest_practice.Mapper.BikeMapper;
import com.example.rest_practice.Service.BikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.PermitAll;
import jakarta.persistence.EntityNotFoundException;
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

    @Operation(summary = "Получение информации о конкретном велосипеде", parameters = {
            @Parameter(name = "id", description = "ID велосипеда", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Велосипед успешно найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BikeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Не существует такого велосипеда")
    })
    @PermitAll
    @GetMapping("/list/{id}")
    public ResponseEntity<?> findBikeById(@PathVariable("id") @Parameter(description = "ID конкретного велосипеда") Long id) {
        try {
            return ResponseEntity.status(200).body(bikeService.findBikeById(id));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
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
