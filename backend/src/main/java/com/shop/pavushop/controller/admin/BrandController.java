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

import com.shop.pavushop.dto.BrandDTO;
import com.shop.pavushop.entity.Brand;
import com.shop.pavushop.mapper.BrandMapper;
import com.shop.pavushop.service.admin.BrandService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/brands")
public class BrandController {
	private final BrandMapper brandMapper;

	@Autowired
	private BrandService brandService;

	@GetMapping
	public ResponseEntity<List<Brand>> getBrands() {
		List<Brand> brands = brandService.brands();
		return ResponseEntity.ok(brands);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addBrand(@RequestBody BrandDTO brandDTO) {
		Brand brand = brandMapper.convertToEntity(brandDTO);
		brandService.addBrand(brand);
		return ResponseEntity.ok("Brand added successfully!");
	}

	@GetMapping("/edit/{id}")
	public ResponseEntity<Brand> editBrand(@PathVariable("id") Integer id) {
		Brand brand = brandService.editBrand(id);
		if (brand == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(brand);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateBrand(@PathVariable("id") Integer id, @Valid @RequestBody BrandDTO brandDTO) {

		Brand brand = brandMapper.convertToEntity(brandDTO);
		brand.setBrandId(id);
		Brand updatedBrand = brandService.updateBrand(brand);

		if (updatedBrand != null) {
			return ResponseEntity.ok("Cập nhật brand thành công");
		} else {
			return ResponseEntity.badRequest().body("Cập nhật thất bại");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteBrand(@PathVariable("id") Integer id) {
		brandService.deleteBrand(id);
		return ResponseEntity.ok("Brand deleted successfully!");
	}
}
