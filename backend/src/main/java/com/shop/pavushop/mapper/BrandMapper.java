package com.shop.pavushop.mapper;

import org.springframework.stereotype.Component;

import com.shop.pavushop.dto.BrandDTO;
import com.shop.pavushop.entity.Brand;
@Component
public class BrandMapper {
	public Brand convertToEntity(BrandDTO brandDTO) {
		Brand brand = new Brand();
		brand.setBrandName(brandDTO.getBrandName());
		return brand;
	}
}
