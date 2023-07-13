package com.example.rest_practice.DTO.Request;

import com.example.rest_practice.Model.Bike;
import com.example.rest_practice.Model.Customer;
import com.example.rest_practice.Model.RentType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rent_request")
public class RentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timeStart;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "rent_type")
    private RentType type;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer renter;
    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;
}
