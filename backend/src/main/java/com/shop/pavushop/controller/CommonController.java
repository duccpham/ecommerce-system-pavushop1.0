package com.shop.pavushop.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common")
public class CommonController {
	@GetMapping("/customer")
	public ResponseEntity<?> getCustomer(Principal principal) {
	    String username = (principal != null) ? principal.getName() : null;
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("username", username);
	    
	    return ResponseEntity.ok(response);
	}
}

