package com.example.rest_practice.DTO.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RentStopResponse {
    private LocalDateTime start;
    private LocalDateTime end;
    private Double price;
    private String totalTime;
}
