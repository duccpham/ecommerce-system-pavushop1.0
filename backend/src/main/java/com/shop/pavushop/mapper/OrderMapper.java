package com.shop.pavushop.mapper;

import org.springframework.stereotype.Component;

import com.shop.pavushop.dto.OrderDTO;
import com.shop.pavushop.entity.Order;

@Component
public class OrderMapper {
	public Order convertToEntity(OrderDTO orderDTO) {
		Order order = new Order();
		order.setDescription(orderDTO.getDescription());
		order.setAddress(orderDTO.getAddress());
		order.setPhone(orderDTO.getPhone());
		order.setReceiver(orderDTO.getReceiver());
		order.setStatus(orderDTO.getStatus());
		order.setTotalPrice(orderDTO.getTotalPrice());
		return order;
	}
}
