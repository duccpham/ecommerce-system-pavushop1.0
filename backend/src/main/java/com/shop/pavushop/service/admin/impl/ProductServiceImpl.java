package com.shop.pavushop.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shop.pavushop.entity.Brand;
import com.shop.pavushop.entity.Category;
import com.shop.pavushop.entity.Product;
import com.shop.pavushop.repository.BrandRepository;
import com.shop.pavushop.repository.CategoryRepository;
import com.shop.pavushop.repository.ProductRepository;
import com.shop.pavushop.service.admin.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Value("${upload.path}")
	private String pathUploadImage;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	BrandRepository brandRepository;

	@Override
	public List<Product> products() {
		List<Product> products = productRepository.findAll();
		return products;
	}

	@Override
	public Product addProduct(Product product) {

		return productRepository.save(product);

	}

	@Override
	public List<Category> CategoryList() {
		return categoryRepository.findAll();
	}

	@Override
	public List<Brand> brandList() {
		return brandRepository.findAll();
	}

	@Override
	public Product editProduct(Integer id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteProduct(Integer id) {
		productRepository.deleteById(id);
	}

	@Override
	public Product updateProduct(Product product) {

	    Product existing = productRepository.findById(product.getProductId()).orElse(null);
	    if (existing == null) return null;
	    
	    existing.setName(product.getName());
	    existing.setPrice(product.getPrice());
	    existing.setDiscount(product.getDiscount());
	    existing.setQuantity(product.getQuantity());
	    existing.setDescription(product.getDescription());
	    existing.setCategory(product.getCategory());
	    existing.setBrand(product.getBrand());
	    if (product.getImage() != null && !product.getImage().isEmpty()) {
	        existing.setImage(product.getImage());
	    }

	    return productRepository.save(existing);
	}

}
