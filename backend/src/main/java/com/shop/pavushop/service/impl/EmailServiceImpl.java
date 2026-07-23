package com.shop.pavushop.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.shop.pavushop.entity.Order;
import com.shop.pavushop.entity.UserInfoEntity;
import com.shop.pavushop.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Value("${shop.admin.email}")
	private String adminEmail;

	@Override
	@Async
	public void sendOtp(String email, String otp) {

		try {

			SimpleMailMessage message = new SimpleMailMessage();

			message.setFrom(fromEmail);
			message.setTo(email);
			message.setSubject("Mã OTP đặt lại mật khẩu");
			message.setText("""
					Xin chào,

					Mã OTP đặt lại mật khẩu của bạn là: %s

					OTP có hiệu lực trong 2 phút.

					Nếu bạn không yêu cầu đổi mật khẩu, hãy bỏ qua email này.

					Trân trọng,
					PavuShop
					""".formatted(otp));

			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Async
	public void sendOrderSuccess(Order order) {

		try {

			UserInfoEntity user = order.getUserInfoEntity();
			SimpleMailMessage message = new SimpleMailMessage();

			message.setFrom(fromEmail);
			message.setTo(user.getEmail());
			message.setSubject("Đặt hàng thành công - PavuShop");
			message.setText("""
					Xin chào %s,

					Cảm ơn bạn đã đặt hàng tại PavuShop.

					==============================

					Mã đơn hàng : %d

					Ngày đặt    : %s

					Tổng tiền   : %.0f VNĐ

					Trạng thái  : Đang giao dịch

					==============================

					Chúng tôi sẽ xử lý đơn hàng của bạn trong thời gian sớm nhất.

					Trân trọng,
					PavuShop
					""".formatted(user.getFullname(), order.getOrderId(), order.getOrderDate(), order.getTotalPrice()));

			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Async
	public void sendNewOrderToAdmin(Order order) {

		try {

			UserInfoEntity user = order.getUserInfoEntity();
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromEmail);
			message.setTo(adminEmail);
			message.setSubject("Thông báo có đơn hàng mới");
			message.setText("""
					Hệ thống vừa nhận được một đơn hàng mới.

					==============================

					Mã đơn hàng : %d

					Khách hàng  : %s

					Email       : %s

					Điện thoại  : %s

					Địa chỉ     : %s

					Tổng tiền   : %.0f VNĐ

					==============================

					Vui lòng đăng nhập hệ thống Admin để xử lý đơn hàng.

					PavuShop
					""".formatted(order.getOrderId(), user.getFullname(), user.getEmail(), order.getPhone(),
					order.getAddress(), order.getTotalPrice()));

			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	@Async
	public void sendOrderPaid(Order order) {

		try {

			UserInfoEntity user = order.getUserInfoEntity();
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromEmail);
			message.setTo(user.getEmail());
			message.setSubject("Đơn hàng đã được xác nhận thanh toán");
			message.setText("""
					Xin chào %s,

					Đơn hàng #%d của bạn đã được xác nhận thanh toán.

					==============================

					Tổng tiền : %.0f VNĐ

					Trạng thái : Đã thanh toán

					==============================

					Cảm ơn bạn đã mua sắm tại PavuShop.

					Trân trọng,
					PavuShop
					""".formatted(user.getFullname(), order.getOrderId(), order.getTotalPrice()));

			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}