package com.shop.pavushop.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shop.pavushop.entity.Product;
import com.shop.pavushop.service.user.ProductDetailService;

@RestController

public class ProductDetailController {

    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping("/api/productDetail/{id}")
    public ResponseEntity<Map<String, Object>> productDetail(@PathVariable("id") Integer productId) {
        Product product = productDetailService.productDetail(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("product", product);
        response.put("productByCategory", productByCategory(product.getCategory().getCategoryId()));

        return ResponseEntity.ok(response);
    }

    private List<Product> productByCategory(Integer categoryId) {
        return productDetailService.productByCategory(categoryId);
    }
}
