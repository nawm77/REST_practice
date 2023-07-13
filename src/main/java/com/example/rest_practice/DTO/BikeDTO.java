package com.example.rest_practice.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class BikeDTO {

    private String make;
    private String model;
    private String serialNumber;
    private Double costPerHour;
    private Double costPerDay;
    private String status;
    private Long customerId;
    private String customerName;
    private String customerLastname;
    private String customerEmail;
}
