package com.shop.pavushop.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shop.pavushop.dto.ProductDTO;
import com.shop.pavushop.entity.Brand;
import com.shop.pavushop.entity.Category;
import com.shop.pavushop.entity.Product;
import com.shop.pavushop.repository.BrandRepository;
import com.shop.pavushop.repository.CategoryRepository;
@Component
public class ProductMapper {
	 @Autowired
	    private CategoryRepository categoryRepo;

	    @Autowired
	    private BrandRepository brandRepo;
	
	public Product convertToEntity (ProductDTO productDTO) {
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setDiscount(productDTO.getDiscount());
		product.setQuantity(productDTO.getQuantity());
		product.setImage(productDTO.getImageURL());
		Category category = categoryRepo.findById(productDTO.getCategoryId()).orElse(null);
        Brand brand = brandRepo.findById(productDTO.getBrandId()).orElse(null);
        product.setCategory(category);
        product.setBrand(brand);

        return product;
		
		
	}
}
