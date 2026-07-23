package com.shop.pavushop.service.user;

import org.springframework.http.ResponseEntity;

import com.shop.pavushop.dto.ForgotPasswordRequest;
import com.shop.pavushop.dto.LoginRequestDto;
import com.shop.pavushop.dto.ResetPasswordRequest;
import com.shop.pavushop.dto.UserRegistrationDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    ResponseEntity<?> login( LoginRequestDto dto, HttpServletResponse response);
    ResponseEntity<String> register( UserRegistrationDto dto);
    ResponseEntity<?> refreshToken( HttpServletRequest request, HttpServletResponse response);
    void forgotPassword( ForgotPasswordRequest request);
    void resetPassword( ResetPasswordRequest request);
}