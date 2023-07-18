package com.example.rest_practice.Model;

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
    private LocalDateTime timeEnd;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "rent_type")
    private RentType type;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer renter;
    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;
    private Double price;
}
