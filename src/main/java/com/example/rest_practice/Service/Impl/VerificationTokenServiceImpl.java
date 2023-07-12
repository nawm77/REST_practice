package com.example.rest_practice.Service.Impl;

import com.example.rest_practice.Model.VerificationToken;
import com.example.rest_practice.Repository.VerificationTokenRepo;
import com.example.rest_practice.Service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepo verificationTokenRepo;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepo verificationTokenRepo) {
        this.verificationTokenRepo = verificationTokenRepo;
    }

    @Override
    public VerificationToken getByToken(String token) {
        return verificationTokenRepo.findByToken(token);
    }
}