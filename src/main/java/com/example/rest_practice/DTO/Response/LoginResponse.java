package com.example.rest_practice.DTO.Response;

import lombok.Builder;

@Builder
public record LoginResponse(String token) {
}
