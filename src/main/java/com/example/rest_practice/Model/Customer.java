package com.example.rest_practice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.List;

@Entity
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @Pattern(regexp = "\\d{10}", message = "Invalid phone number format")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    @CreditCardNumber(message = "Invalid CC number")
    private String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message="Must be formatted MM/YY")
    private String ccExpiration;
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;
    private Double rate;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
    private DocumentInformation documentInformation;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Bike> bikes;
    @ManyToOne
    @JoinColumn(name = "status_id", columnDefinition = "bigint default 1")
    private Status status;
    @OneToMany(mappedBy = "renter", fetch = FetchType.EAGER)
    private List<RentRequest> rentList;
}
