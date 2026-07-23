package com.shop.pavushop.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.pavushop.entity.Brand;
import com.shop.pavushop.entity.Category;
import com.shop.pavushop.entity.Product;

@Service
public interface ProductService {
	
    public List<Product> products() ;
	
	public Product updateProduct(Product product);
	public Product addProduct(Product product) ;
	public List<Category> CategoryList() ;
	public List<Brand> brandList();
	public Product editProduct(Integer id) ;
	public void deleteProduct(Integer id) ;

}
