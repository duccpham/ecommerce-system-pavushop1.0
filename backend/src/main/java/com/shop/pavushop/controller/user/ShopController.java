package com.shop.pavushop.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.pavushop.service.user.ShopService;

@RestController
@RequestMapping("/api/shop")
public class ShopController{
    
    @Autowired
    private ShopService shopService;
    
    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getProducts() {
        Map<String, Object> response = new HashMap<>();
        response.put("productList", shopService.productList());
        response.put("categoryList", shopService.CategoryList());
        response.put("brandList", shopService.brandList());
        response.put("countProductByCategory", shopService.countProductByCategoryName());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/productByCategory/{id}")
    public ResponseEntity<Map<String, Object>> getProductByCategory(@PathVariable("id") Integer id) {
        Map<String, Object> response = new HashMap<>();
        response.put("productList", shopService.listProductByCategoryId(id));
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/productByBrand/{id}")
    public ResponseEntity<Map<String, Object>> getProductByBrand(@PathVariable("id") Integer id) {
        Map<String, Object> response = new HashMap<>();
        response.put("productList", shopService.listProductByBrandId(id));
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/searchProduct")
    public ResponseEntity<Map<String, Object>> searchProduct(@RequestParam("keyword") String keyword) {
        Map<String, Object> response = new HashMap<>();
        response.put("productList", shopService.showSearch(keyword));
        response.put("categoryList", shopService.CategoryList());
        response.put("brandList", shopService.brandList());
        response.put("countProductByCategory", shopService.countProductByCategoryName());
        return ResponseEntity.ok(response);
    }
}
