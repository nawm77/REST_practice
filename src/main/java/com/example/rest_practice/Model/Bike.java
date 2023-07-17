package com.example.rest_practice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private Double costPerDay;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private RentStatus status;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Customer customer;
    @OneToMany(mappedBy = "bike", fetch = FetchType.LAZY)
    private List<RentRequest> rentList;
}
