package com.shop.pavushop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.pavushop.dto.CountProductByCategoryDTO;
import com.shop.pavushop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findTop8ByOrderByEnteredDateDesc();

	
	@Query(value = "SELECT c.category_id AS categoryId, COUNT(*) AS count "
			+ "FROM products p JOIN categories c "
			+ "ON p.category_id = c.category_id GROUP BY c.category_id", nativeQuery = true)
	List<CountProductByCategoryDTO> countProductByCategory();

	List<Product> findByCategoryCategoryId(Integer categoryId);

	List<Product> findByBrandBrandId(Integer brandId);

	List<Product> findByNameContaining(String keyword);

}
