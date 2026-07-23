package com.shop.pavushop.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.pavushop.service.user.IndexService;

@RestController
@RequestMapping("/api")
public class IndexController  {

	@Autowired
	IndexService indexService;
	
	@GetMapping("/home")
	public ResponseEntity<Map<String, Object>> index() {
		Map<String, Object> response = new HashMap<>();
		response.put("productList", indexService.listProduct8());
		response.put("topOrderList", indexService.topOrder());
		return ResponseEntity.ok(response);
	}
}
