package com.example.rest_practice.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<Customer> customer = new HashSet<>();
//    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
//    private Set<CustomerRole> customerRoles = new HashSet<>();
}
