package com.shop.pavushop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.pavushop.dto.CategoryDto;
import com.shop.pavushop.entity.Category;
import com.shop.pavushop.mapper.CategoryMapper;
import com.shop.pavushop.service.admin.CategoryService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/categories")
public class CategoryController {
	private final CategoryMapper categoryMapper;
	@Autowired
	CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<Category>> getCategories() {
		List<Category> categories = categoryService.categories();
		return ResponseEntity.ok(categories);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
		Category category = categoryMapper.convertToEntity(categoryDto);
		categoryService.addCategory(category);
		return ResponseEntity.ok("Category added successfully!");
	}

	@GetMapping("/edit/{id}")
	public ResponseEntity<Category> editCategory(@PathVariable("id") Integer id) {
		Category category = categoryService.editCategory(id);
		if (category == null) {
			return ResponseEntity.notFound().build();

		}
		return ResponseEntity.ok(category);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateCategory(@PathVariable("id") Integer id,
			@Valid @RequestBody CategoryDto categoryDto) {

		Category category = categoryMapper.convertToEntity(categoryDto);
		category.setCategoryId(id);
		Category updatedCategory = categoryService.updateCategory(category);

		if (updatedCategory != null) {
			return ResponseEntity.ok("Cập nhật category thành công");
		} else {
			return ResponseEntity.badRequest().body("Cập nhật thất bại");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.ok("Category deleted successfully!");

	}
}
