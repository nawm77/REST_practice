package com.example.rest_practice.DTO.Request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@Data
public class LoginRequest {
    private String email;
    private String password;
}
