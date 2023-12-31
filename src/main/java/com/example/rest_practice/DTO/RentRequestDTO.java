package com.example.rest_practice.DTO;

import com.example.rest_practice.Model.RentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RentRequestDTO {
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private RentType type;
    private String bikeMake;
    private String bikeModel;
    @NotBlank(message = "Serial number is required")
    @Size(min = 4, max = 16, message = "Serial number length must be between 8 and 16")
    private String bikeSerialNumber;
    private String customerName;
    private String customerSurname;
    private Double price;
}
