package com.example.rest_practice.DTO.Response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
public class RentStopResponse {
    private LocalDateTime start;
    private LocalDateTime end;
    private Double price;
    private String totalTime;
}
