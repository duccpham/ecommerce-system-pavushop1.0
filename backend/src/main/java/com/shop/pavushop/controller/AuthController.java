package com.shop.pavushop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.pavushop.dto.ForgotPasswordRequest;
import com.shop.pavushop.dto.LoginRequestDto;
import com.shop.pavushop.dto.ResetPasswordRequest;
import com.shop.pavushop.dto.UserRegistrationDto;
import com.shop.pavushop.service.user.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequestDto dto,
            HttpServletResponse response) {

        return authService.login(dto, response);
    }

    @PostMapping("/api/register")
    public ResponseEntity<String> register(
            @RequestBody UserRegistrationDto dto) {

        return authService.register(dto);
    }

    @PostMapping("/api/refresh-token")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        return authService.refreshToken(request, response);
    }
    
    @PostMapping("/api/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        try {

            authService.forgotPassword(request);

            return ResponseEntity.ok("OTP đã được gửi");

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }
    
    @PostMapping("/api/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request) {

        try {

            authService.resetPassword(request);

            return ResponseEntity.ok("Đổi mật khẩu thành công");

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }
}















