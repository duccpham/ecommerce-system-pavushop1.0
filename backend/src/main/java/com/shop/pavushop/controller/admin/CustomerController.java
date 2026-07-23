package com.shop.pavushop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.pavushop.entity.UserInfoEntity;
import com.shop.pavushop.service.admin.CustomerService;

@RestController
@RequestMapping("/api/admin/customer")
public class CustomerController  {
	@Autowired
	CustomerService customerService;

	@GetMapping
	public ResponseEntity<List<UserInfoEntity>> getUser() {
		List<UserInfoEntity> allUser = customerService.customer();
		return ResponseEntity.ok(allUser);

	}

}
