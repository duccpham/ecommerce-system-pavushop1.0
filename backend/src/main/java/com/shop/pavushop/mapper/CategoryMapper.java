package com.shop.pavushop.mapper;

import org.springframework.stereotype.Component;

import com.shop.pavushop.dto.CategoryDto;
import com.shop.pavushop.entity.Category;
@Component
public class CategoryMapper {

	public Category convertToEntity(CategoryDto categoryDto) {
		Category category = new Category();
		category.setCategoryName(categoryDto.getCategoryName());
		return category;
	}
}
