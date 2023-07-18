package com.example.rest_practice.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class BikeDTO {
    @NotBlank(message = "Make is required")
    @Size(min = 2, max = 16, message = "Make length must be between 2 and 16")
    private String make;
    @NotBlank(message = "Model is required")
    @Size(min = 2, max = 16, message = "Model length must be between 2 and 16")
    private String model;
    @NotBlank(message = "Serial number is required")
    @Size(min = 4, max = 16, message = "Serial number length must be between 8 and 16")
    private String serialNumber;
    private Double costPerHour;
    private Double costPerDay;
    private String status;
    private Long customerId;
    private String customerName;
    private String customerSurname;
    private String customerEmail;
}
