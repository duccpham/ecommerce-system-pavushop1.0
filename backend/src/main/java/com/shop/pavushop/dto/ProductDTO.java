package com.shop.pavushop.dto;

import lombok.Data;

@Data
public class ProductDTO {
	
	private String name;
	private String imageURL;
	private double price;
	private double discount;
	private int quantity;
	private String description;
	private int categoryId;
    private int brandId;
	
}
