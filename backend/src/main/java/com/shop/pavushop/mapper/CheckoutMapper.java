package com.shop.pavushop.mapper;

import org.springframework.stereotype.Component;

import com.shop.pavushop.dto.CheckoutDTO;
import com.shop.pavushop.entity.Order;
@Component
public class CheckoutMapper {
	public Order convertToEntity (CheckoutDTO checkoutDTO) {
		Order order = new Order();
		order.setAddress(checkoutDTO.getAddress());
		order.setDescription(checkoutDTO.getDescription());
		order.setPhone(checkoutDTO.getPhone());
		order.setReceiver(checkoutDTO.getReceiver());
		return order;
		
	}

}
