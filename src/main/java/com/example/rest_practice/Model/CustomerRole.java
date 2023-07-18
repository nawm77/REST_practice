package com.example.rest_practice.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users_roles")
@Getter
@Setter
public class CustomerRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public CustomerRole(Customer customer, Role role) {
        this.customer = customer;
        this.role = role;
    }

    public CustomerRole() {

    }
}
