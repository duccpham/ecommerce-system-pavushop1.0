package com.shop.pavushop.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.pavushop.entity.Order;
import com.shop.pavushop.enums.OrderStatus;
import com.shop.pavushop.repository.OrderRepository;
import com.shop.pavushop.service.EmailService;
import com.shop.pavushop.service.admin.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	EmailService emailService;
	@Override
	public List<Order> orders() {
		return orderRepository.listOrderByDesc();	
		}

	@Override
	public Order showEditOrder(int order) {
		return orderRepository.findById(order).orElse(null);
	}

	@Override
	public Order updateOrder(Order order) {

	    Order existing = orderRepository.findById(order.getOrderId())
	            .orElse(null);
	    if (existing == null) {
	        return null;
	    }

	    String oldStatus = existing.getStatus();

	    existing.setDescription(order.getDescription());
	    existing.setReceiver(order.getReceiver());
	    existing.setAddress(order.getAddress());
	    existing.setStatus(order.getStatus());
	    existing.setTotalPrice(order.getTotalPrice());
	    existing.setPhone(order.getPhone());

	    Order savedOrder = orderRepository.save(existing);

	    if (!oldStatus.equals(savedOrder.getStatus())) {
	        if (OrderStatus.PAID.getValue().equals(savedOrder.getStatus())) {
	            emailService.sendOrderPaid(savedOrder);
	        }
	    }
	    return savedOrder;
	}
}
