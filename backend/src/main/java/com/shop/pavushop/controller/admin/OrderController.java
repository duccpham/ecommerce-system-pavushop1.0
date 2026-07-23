package com.shop.pavushop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.pavushop.dto.OrderDTO;
import com.shop.pavushop.entity.Order;
import com.shop.pavushop.mapper.OrderMapper;
import com.shop.pavushop.service.admin.OrderService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/orders")
public class OrderController {
	private final OrderMapper orderMapper;
	@Autowired
	OrderService orderService;
	
	
	@GetMapping
	public ResponseEntity<List<Order>> getOrders (){
		List<Order> Orders = orderService.orders();
		return ResponseEntity.ok(Orders);
	}
	
	@GetMapping("/editorder/{id}")
	public ResponseEntity<Order> getEditOrder (@PathVariable ("id") Integer id){
		Order order = orderService.showEditOrder(id);
		return ResponseEntity.ok(order);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateOrder(@PathVariable("id") Integer id, @Valid @RequestBody OrderDTO orderDTO) {

		Order order = orderMapper.convertToEntity(orderDTO);
		order.setOrderId(id);
		Order updatedOrder = orderService.updateOrder(order);

		if (updatedOrder != null) {
			return ResponseEntity.ok("Cập nhật order thành công");
		} else {
			return ResponseEntity.badRequest().body("Cập nhật thất bại");
		}
	}
	
		
		
	

}
