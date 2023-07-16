package com.example.rest_practice.DTO;

import com.example.rest_practice.Model.RentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class RentRequestDTO {
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private RentType type;
    private String bikeMake;
    private String bikeModel;
    private String bikeSerialNumber;
    private String customerName;
    private String customerSurname;

}
