package com.shop.pavushop.config.jwtAuth;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shop.pavushop.config.jwtAuth.utils.CookieConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAccessTokenFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        Optional<String> tokenOptional = getTokenFromCookies(request);
        if (tokenOptional.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = tokenOptional.get();
        Jwt jwtToken = jwtDecoder.decode(token);

        
        Instant expiresAt = jwtToken.getExpiresAt();
        if (expiresAt == null || expiresAt.isBefore(Instant.now())) {
            filterChain.doFilter(request, response);
            return;
        }
        
        
        String userName = jwtToken.getSubject();

        if (userName != null && !userName.isEmpty() &&
            SecurityContextHolder.getContext().getAuthentication() == null) {
            List<String> authoritiesClaim = (List<String>) jwtToken.getClaim("authorities");

            List<GrantedAuthority> grantedAuthorities = authoritiesClaim.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userName,
                            null,
                            grantedAuthorities
                    );


            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> CookieConstants.JWT_COOKIE_NAME.equals(cookie.getName()))
                .map(cookie -> cookie.getValue())
                .findFirst();
    }
}
