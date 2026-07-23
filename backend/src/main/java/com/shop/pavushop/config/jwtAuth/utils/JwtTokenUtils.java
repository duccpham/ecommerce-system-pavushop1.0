package com.shop.pavushop.config.jwtAuth.utils;

import java.time.Instant;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    public String getUserName(Jwt jwtToken){
        return jwtToken.getSubject();
    }

    public boolean getIfTokenIsExpired(Jwt jwtToken) {
        return jwtToken.getExpiresAt().isBefore(Instant.now());
    }

 }

