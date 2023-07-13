package com.example.rest_practice.Model;

import com.example.rest_practice.DTO.Request.RentRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String make;
    private String model;
    private String serialNumber;
    private Double costPerHour;
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
