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
    @NotBlank(message = "Make is required")
    @Size(min = 2, max = 16, message = "Make length must be between 2 and 16")
    private String bikeMake;
    @NotBlank(message = "Model is required")
    @Size(min = 2, max = 16, message = "Model length must be between 2 and 16")
    private String bikeModel;
    @NotBlank(message = "Serial number is required")
    @Size(min = 8, max = 16, message = "Serial number length must be between 8 and 16")
    private String bikeSerialNumber;
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 16, message = "Name length must be between 2 and 16")
    private String customerName;
    @NotBlank(message = "Surname is required")
    @Size(min = 2, max = 16, message = "Surname length must be between 2 and 16")
    private String customerSurname;

}
