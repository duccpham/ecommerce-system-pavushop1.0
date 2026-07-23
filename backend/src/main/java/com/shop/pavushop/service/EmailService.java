package com.shop.pavushop.service;

import com.shop.pavushop.entity.Order;

public interface EmailService {
	
	void sendOtp (String email,String otp);
    void sendOrderSuccess(Order order);
    void sendOrderPaid(Order order);
    void sendNewOrderToAdmin(Order order);
}
