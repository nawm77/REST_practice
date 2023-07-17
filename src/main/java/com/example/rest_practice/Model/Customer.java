package com.example.rest_practice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    @Size(min = 4, max = 16, message = "Username length must be between 4 and 16")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must be between 4 and 32", max = 32)
    private String password;
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 16, message = "Name length must be between 2 and 16")
    private String name;
    @NotBlank(message = "Surname is required")
    @Size(min = 2, max = 16, message = "Surname length must be between 2 and 16")
    private String surname;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Size(min = 4, max = 32, message = "Email length must be more than 4")
    private String email;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
    private DocumentInformation documentInformation;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Bike> bikes;
    @OneToMany(mappedBy = "renter", fetch = FetchType.EAGER)
    private List<RentRequest> rentList;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> role = new HashSet<>();
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
