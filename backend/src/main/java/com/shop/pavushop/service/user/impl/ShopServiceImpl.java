package com.shop.pavushop.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.pavushop.dto.CountProductByCategoryDTO;
import com.shop.pavushop.entity.Brand;
import com.shop.pavushop.entity.Category;
import com.shop.pavushop.entity.Product;
import com.shop.pavushop.repository.BrandRepository;
import com.shop.pavushop.repository.CategoryRepository;
import com.shop.pavushop.repository.ProductRepository;
import com.shop.pavushop.service.user.ShopService;

@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	ProductRepository productRepository;

	@Autowired
	BrandRepository brandRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Product> productList() {
		return productRepository.findAll();
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
	public List<Product> listProductByCategoryId(Integer id) {
		return productRepository.findByCategoryCategoryId(id);
	}

	@Override
	public Map<Integer, Long> countProductByCategoryName() {
        List<CountProductByCategoryDTO> result = productRepository.countProductByCategory();
        Map<Integer, Long> map = new HashMap<>();


        for (CountProductByCategoryDTO dto : result) {
            map.put(dto.getCategoryId(), dto.getCount());
        }

        return map;
    }

	@Override
	public List<Product> listProductByBrandId(Integer id) {
		return productRepository.findByBrandBrandId(id);
	}

	@Override
	public List<Product> showSearch(String keyword) {
		return productRepository.findByNameContaining(keyword);
	}

}
