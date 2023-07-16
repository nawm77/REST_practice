package com.example.rest_practice.DTO.Response;

import com.example.rest_practice.Model.RentType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BikeRentResponse {
    private Long bikeId;
    private LocalDateTime timeStart;
    private RentType rentType;
}
