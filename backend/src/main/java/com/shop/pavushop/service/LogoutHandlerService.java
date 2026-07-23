package com.shop.pavushop.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.shop.pavushop.config.jwtAuth.utils.CookieConstants;
import com.shop.pavushop.repository.RefreshTokenRepo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogoutHandlerService implements LogoutHandler {

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> accessTokenOpt = getTokenFromCookies(request, CookieConstants.JWT_COOKIE_NAME);
        Optional<String> refreshTokenOpt = getTokenFromCookies(request, CookieConstants.REFRESHJWT_COOKIE_NAME);

        // Xóa access token cookie
        accessTokenOpt.ifPresent(token -> deleteCookie(response, CookieConstants.JWT_COOKIE_NAME));

        // Xóa refresh token cookie + DB
        refreshTokenOpt.ifPresent(refreshToken -> {
            deleteCookie(response, CookieConstants.REFRESHJWT_COOKIE_NAME);
            refreshTokenRepo.findByRefreshToken(refreshToken)
                .ifPresent(refreshTokenEntity -> refreshTokenRepo.delete(refreshTokenEntity));
        });
    }

    private Optional<String> getTokenFromCookies(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    private void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
