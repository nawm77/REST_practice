package com.example.rest_practice.Service;

import com.example.rest_practice.Model.VerificationToken;

public interface VerificationTokenService {
    VerificationToken getByToken(String token);
}