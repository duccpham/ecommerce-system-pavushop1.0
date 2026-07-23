package com.shop.pavushop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationDto {
	String userName;
	String email;
	String userPassword;
	String fullName;
}
