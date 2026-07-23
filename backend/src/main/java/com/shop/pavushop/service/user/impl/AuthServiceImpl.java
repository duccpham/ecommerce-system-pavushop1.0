package com.shop.pavushop.service.user.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import com.shop.pavushop.config.jwtAuth.JwtTokenGenerator;
import com.shop.pavushop.config.jwtAuth.RSAKeyRecord;
import com.shop.pavushop.config.jwtAuth.utils.CookieConstants;
import com.shop.pavushop.config.jwtAuth.utils.JwtTokenUtils;
import com.shop.pavushop.config.user.UserInfoManagerConfig;
import com.shop.pavushop.dto.ForgotPasswordRequest;
import com.shop.pavushop.dto.LoginRequestDto;
import com.shop.pavushop.dto.LoginResponseDto;
import com.shop.pavushop.dto.ResetPasswordRequest;
import com.shop.pavushop.dto.UserRegistrationDto;
import com.shop.pavushop.entity.RefreshTokenEntity;
import com.shop.pavushop.entity.Role;
import com.shop.pavushop.entity.UserInfoEntity;
import com.shop.pavushop.mapper.UserInfoMapper;
import com.shop.pavushop.repository.RefreshTokenRepo;
import com.shop.pavushop.repository.RoleRepository;
import com.shop.pavushop.repository.UserInfoRepo;
import com.shop.pavushop.repository.UserRepository;
import com.shop.pavushop.service.EmailService;
import com.shop.pavushop.service.OtpService;
import com.shop.pavushop.service.user.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepo refreshTokenRepo;
    private final UserInfoRepo userInfoRepo;
    private final UserInfoMapper userInfoMapper;
    private final JwtTokenGenerator jwtGenerator;
    private final JwtTokenUtils jwtTokenUtils;
    private final RSAKeyRecord rsaKeyRecord;
    private final AuthenticationManager authenticationManager;
    private final UserInfoManagerConfig userInfoManagerConfig;
    private final UserRepository userRepository;
    private final OtpService otpService;
    private final EmailService emailService;

    
    @Override
    public ResponseEntity<?> login(LoginRequestDto loginDto,
                                   HttpServletResponse response) {

        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginDto.getUsername(),
                                    loginDto.getPassword()));

            String accessToken =
                    jwtGenerator.generateAccessToken(authentication);

            String refreshToken =
                    jwtGenerator.generateRefreshToken(authentication);

            Cookie jwtCookie = new Cookie("jwt", accessToken);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(900);

            response.addCookie(jwtCookie);

            Cookie refreshCookie =
                    new Cookie("refreshToken", refreshToken);

            refreshCookie.setHttpOnly(true);
            refreshCookie.setPath("/");
            refreshCookie.setMaxAge(15 * 24 * 60 * 60);

            response.addCookie(refreshCookie);

            String role =
                    authentication.getAuthorities()
                            .stream()
                            .findFirst()
                            .map(GrantedAuthority::getAuthority)
                            .orElse("USER");

            UserInfoEntity user =
                    userInfoRepo.findByUsername(loginDto.getUsername())
                            .orElseThrow(() ->
                                    new RuntimeException("User not found"));

            RefreshTokenEntity tokenEntity =
                    RefreshTokenEntity.builder()
                            .refreshToken(refreshToken)
                            .revoked(false)
                            .user(user)
                            .build();

            refreshTokenRepo.save(tokenEntity);

            return ResponseEntity.ok(
                    new LoginResponseDto(
                            "Login successful",
                            user.getUsername(),
                            role));

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }

    @Override
    public ResponseEntity<String> register(
            UserRegistrationDto userRegistrationDto) {

        UserInfoEntity user =
                userInfoMapper.convertToEntity(userRegistrationDto);

        if (userInfoRepo.existsByUsername(user.getUsername())) {

            return new ResponseEntity<>(
                    "Username is taken!",
                    HttpStatus.BAD_REQUEST);
        }

        Role role =
                roleRepository.findByName("USER")
                        .orElseThrow();

        user.setRoles(Collections.singletonList(role));

        userInfoRepo.save(user);

        return new ResponseEntity<>(
                "User registered success!",
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        try {

            Optional<String> refreshTokenOpt =
                    Arrays.stream(
                                    Optional.ofNullable(request.getCookies())
                                            .orElse(new Cookie[0]))
                            .filter(cookie ->
                                    CookieConstants
                                            .REFRESHJWT_COOKIE_NAME
                                            .equals(cookie.getName()))
                            .map(Cookie::getValue)
                            .findFirst();

            if (refreshTokenOpt.isEmpty()) {

                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Refresh token not found");
            }

            String refreshToken = refreshTokenOpt.get();

            Optional<RefreshTokenEntity> tokenEntityOpt =
                    refreshTokenRepo.findByRefreshToken(refreshToken);

            if (tokenEntityOpt.isEmpty()) {

                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Refresh token invalid");
            }

            RefreshTokenEntity tokenEntity =
                    tokenEntityOpt.get();

            if (tokenEntity.isRevoked()) {

                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Refresh token is revoked");
            }

            Jwt jwt =
                    NimbusJwtDecoder
                            .withPublicKey(rsaKeyRecord.rsaPublicKey())
                            .build()
                            .decode(refreshToken);

            if (jwtTokenUtils.getIfTokenIsExpired(jwt)) {

                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Refresh token expired");
            }

            UserInfoEntity user =
                    tokenEntity.getUser();

            UserDetails userDetails =
                    userInfoManagerConfig
                            .loadUserByUsername(user.getUsername());

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

            String newAccessToken =
                    jwtGenerator.generateAccessToken(authentication);

            Cookie newAccessCookie =
                    new Cookie("jwt", newAccessToken);

            newAccessCookie.setHttpOnly(true);
            newAccessCookie.setPath("/");
            newAccessCookie.setMaxAge(900);

            response.addCookie(newAccessCookie);

            return ResponseEntity.ok("Access token refreshed");

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid refresh token");
        }
    }

	@Override
	public void forgotPassword(ForgotPasswordRequest request) {
		UserInfoEntity user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() ->
				new RuntimeException("Email không tồn tại"));
		
		String otp = otpService.generateOtp();
		otpService.saveOtp(request.getEmail(), otp);
		emailService.sendOtp(request.getEmail(), otp);
	}

	
	@Override
	public void resetPassword(ResetPasswordRequest request) {

	    if (!otpService.verifyOtp(request.getEmail(), request.getOtp())) {
	        throw new RuntimeException("OTP không hợp lệ hoặc đã hết hạn");
	    }

	    UserInfoEntity user = userRepository.findByEmail(request.getEmail())
	            .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

	    if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
	        throw new RuntimeException("Mật khẩu mới không được trùng với mật khẩu cũ");
	    }

	    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
	    userRepository.save(user);
	    otpService.deleteOtp(request.getEmail());
	}
}














