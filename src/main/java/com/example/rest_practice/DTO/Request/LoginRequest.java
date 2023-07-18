package com.example.rest_practice.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Email is required")
    @Size(min = 4, max = 32, message = "Email length must be more than 4")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 32, message = "Password length must be more than 4")
    private String password;
}
