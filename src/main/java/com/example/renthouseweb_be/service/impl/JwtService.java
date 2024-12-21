package com.example.renthouseweb_be.service.impl;

import com.example.renthouseweb_be.model.account.UserPrinciple;
import com.example.renthouseweb_be.security.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;

@Service
public class JwtService {

    private static final long EXPIRE_TIME = 86400000000L;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class.getName());

    private final JwtTokenProvider jwtTokenProvider;

    public JwtService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String generateTokenLogin(Authentication authentication) {
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        int isOwner = determineIsOwner(userPrincipal);
        SecretKey secretKey = jwtTokenProvider.getKey();
        return jwtTokenProvider.generateToken(userPrincipal.getUsername(), EXPIRE_TIME, isOwner, secretKey);
    }

    public boolean validateJwtToken(String authToken) {
        SecretKey secretKey = jwtTokenProvider.getKey();
        return jwtTokenProvider.validateToken(authToken, secretKey);
    }

    public String getUserNameFromJwtToken(String token) {
        return jwtTokenProvider.getUsernameFromToken(token);
    }

    public int getIsOwnerFromJwtToken(String token) {
        return jwtTokenProvider.getIsOwnerFromToken(token);
    }

    private int determineIsOwner(UserPrinciple userPrincipal) {
        // Implement your logic to determine isOwner based on the numeric value in isOwner field
        // Replace this with your actual logic.

        int isOwnerValue = userPrincipal.getIsOwner();

        if (isOwnerValue == 2) {
            return 2; // User has the highest authority for managing house rentals
        } else if (isOwnerValue == 1) {
            return 1; // User is pending for admin approval to become an owner
        } else {
            return 0; // User is a regular user without authority for managing house rentals
        }
    }
}
