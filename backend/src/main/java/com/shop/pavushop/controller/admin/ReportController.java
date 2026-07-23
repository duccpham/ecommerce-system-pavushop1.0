package com.shop.pavushop.controller.admin;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.pavushop.service.admin.ReportService;

@RestController
@RequestMapping("/api/admin/report")
public class ReportController {

	@Autowired
	ReportService reportService;

	// thống kê theo sản phẩm được bán
	@GetMapping("/products")
	public ResponseEntity<List<Object[]>> reportProduct() throws SQLException {
		List<Object[]> reportProduct = reportService.reportProduct();
		return ResponseEntity.ok(reportProduct);
	}

	// thống kê theo thể loại được bán
	@GetMapping("/Category")
	public ResponseEntity<List<Object[]>> reportCategory() throws SQLException {
		List<Object[]> reportCategory = reportService.reportProduct();
		return ResponseEntity.ok(reportCategory);
	}

	// tống kê theo sản phẩm được bán
	@GetMapping("Brands")
	public ResponseEntity<List<Object[]>> reportBrands() throws SQLException {
		List<Object[]> reportBrands = reportService.reportBrands();
		return ResponseEntity.ok(reportBrands);

	}

	// thống kê sản phẩm bán ra theo năm
	@GetMapping("Year")
	public ResponseEntity<List<Object[]>> reportyear() throws SQLException {
		List<Object[]> reportYear = reportService.reportYear();
		return ResponseEntity.ok(reportYear);
	}

	// thống kê sản phẩam bán ra theo tháng
	@GetMapping("/Month")
	public ResponseEntity<List<Object[]>> reportmonth() throws SQLException {
		List<Object[]> reportMonth = reportService.reportMonth();
		return ResponseEntity.ok(reportMonth);
	}

	// hống kê sản phẩm bán ra theo quý
	@GetMapping("/Quarter")
	public ResponseEntity<List<Object[]>> reportquarter() throws SQLException {
		List<Object[]> reportQuarter = reportService.reportQuarter();
		return ResponseEntity.ok(reportQuarter);

	}

	// thống kê theo người dùng
	@GetMapping("/OrderCustomer")
	public ResponseEntity<List<Object[]>> reportordercustomer() throws SQLException {
		List<Object[]> reportordercustomer = reportService.reportOrderCustomer();
		return ResponseEntity.ok(reportordercustomer);

	}

}
