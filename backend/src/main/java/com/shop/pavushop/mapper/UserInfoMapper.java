package com.shop.pavushop.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.shop.pavushop.dto.UserRegistrationDto;
import com.shop.pavushop.entity.UserInfoEntity;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class UserInfoMapper {
	private final PasswordEncoder passwordEncoder;

	public UserInfoEntity convertToEntity(UserRegistrationDto userRegistrationDto) {
		UserInfoEntity userInfoEntity = new UserInfoEntity();
		userInfoEntity.setUsername(userRegistrationDto.getUserName());
		userInfoEntity.setEmail(userRegistrationDto.getEmail());
		userInfoEntity.setPassword(passwordEncoder.encode(userRegistrationDto.getUserPassword()));
		userInfoEntity.setFullname(userRegistrationDto.getFullName());
		return userInfoEntity;

	}
}
