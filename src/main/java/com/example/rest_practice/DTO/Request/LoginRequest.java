package com.example.rest_practice.DTO.Request;

import lombok.*;

@Getter
@Builder
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
