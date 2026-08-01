package com.shop.pavushop.service.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.shop.pavushop.entity.Brand;
import com.shop.pavushop.entity.Category;
import com.shop.pavushop.entity.Product;



@Service
public interface ShopService {
	
	public List<Product> productList();
	
	public List<Category> CategoryList();
	
	public List<Brand> brandList();
	
	public List<Product> listProductByCategoryId(Integer id);
	
	public Map<Integer, Long> countProductByCategoryName() ;
	
	public List<Product> listProductByBrandId(Integer id) ;
	
	public List<Product> showSearch( String keyword);

}
