package com.example.rest_practice.Controller;

import com.example.rest_practice.DTO.RentRequestDTO;
import com.example.rest_practice.DTO.Response.RentStopResponse;
import com.example.rest_practice.Service.RentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/rent")
public class RentController {
    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }
    @GetMapping("/list")
    public ResponseEntity<List<RentRequestDTO>> getRentList(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(rentService.findAllByCustomer(userDetails));
    }
    @PostMapping("/start")
    public ResponseEntity<?> addNewRequest(@RequestBody @Valid RentRequestDTO dto, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        rentService.startRent(dto, userDetails);
        return ResponseEntity.status(201).build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRentById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        rentService.deleteById(id, userDetails);
        return ResponseEntity.status(204).build();
    }
    @PostMapping("/stop/{id}")
    public ResponseEntity<RentStopResponse> editRentById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        return ResponseEntity.status(201).body(rentService.stopRentById(id, userDetails, LocalDateTime.now()));
    }
}
