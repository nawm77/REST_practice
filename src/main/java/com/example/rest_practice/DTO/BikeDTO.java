package com.example.rest_practice.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
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
    @Size(min = 8, max = 16, message = "Serial number length must be between 8 and 16")
    private String serialNumber;
    @NotBlank(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Value must be more than 0")
    private Double costPerHour;
    @NotBlank(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Value must be more than 0")
    private Double costPerDay;
    private String status;
    @NotBlank(message = "Customer id is required")
    private Long customerId;
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 16, message = "Name length must be between 2 and 16")
    private String customerName;
    @NotBlank(message = "Surname is required")
    @Size(min = 2, max = 16, message = "Surname length must be between 2 and 16")
    private String customerSurname;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Size(min = 4, max = 32, message = "Email length must be more than 4")
    private String customerEmail;
}
