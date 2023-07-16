package com.example.rest_practice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
//    @CreditCardNumber(message = "Invalid CC number")
//    @NotBlank(message = "Credit card number is required")
//    private String ccNumber;
//    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message="Must be formatted MM/YY")
//    @NotBlank(message = "Credit card expiration is required")
//    private String ccExpiration;
//    @Digits(integer=3, fraction=0, message="Invalid CVV")
//    @NotBlank(message = "Credit card CVV is required")
//    private String ccCVV;
//    private Double rate;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
    private DocumentInformation documentInformation;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Bike> bikes;
    @OneToMany(mappedBy = "renter", fetch = FetchType.EAGER)
    private List<RentRequest> rentList;
//    private Integer rentCount;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> role = new ArrayList<>();
    private Boolean isNonBlocked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isNonBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", documentInformation=" + documentInformation +
                ", bikes=" + bikes +
                ", rentList=" + rentList +
                ", role=" + role +
                '}';
    }
}
