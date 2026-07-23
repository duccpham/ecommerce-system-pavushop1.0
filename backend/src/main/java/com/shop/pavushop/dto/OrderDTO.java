package com.shop.pavushop.dto;

import lombok.Data;

@Data
public class OrderDTO {
	private String description;
	private String receiver;
	private String address;
	private String status;
	private Double totalPrice;
	private String phone;
	
}
