package com.shop.pavushop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class LoginResponseDto {
	
	private String message;
	private String username;
	private String role;
}
