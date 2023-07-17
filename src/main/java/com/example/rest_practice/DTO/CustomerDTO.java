package com.example.rest_practice.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerDTO {
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
    @NotBlank(message = "Email is required")
    @Size(min = 4, max = 32, message = "Email length must be more than 4")
    private String email;
    private Boolean isNonBlocked;
}
