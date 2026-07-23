package com.shop.pavushop.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.pavushop.dto.ProductDTO;
import com.shop.pavushop.entity.Product;
import com.shop.pavushop.mapper.ProductMapper;
import com.shop.pavushop.service.admin.ProductService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/products")
public class ProductController {
    private final ProductMapper productMapper;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.products());
    }
    
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) {
        Product product = productMapper.convertToEntity(productDTO);
        Product p = productService.addProduct(product);

        if (p != null) {
            return ResponseEntity.ok("Thêm sản phẩm thành công");
        } else {
            return ResponseEntity.badRequest().body("Thêm sản phẩm thất bại");
        }
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(productService.editProduct(id));
    }

   
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable("id") Integer id,
            @RequestBody ProductDTO productDTO) {

        Product product = productMapper.convertToEntity(productDTO);
        product.setProductId(id);

        Product updatedProduct = productService.updateProduct(product);

        if (updatedProduct != null) {
            return ResponseEntity.ok("Cập nhật sản phẩm thành công");
        } else {
            return ResponseEntity.badRequest().body("Cập nhật thất bại");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Xóa sản phẩm thành công!");
    }
}


