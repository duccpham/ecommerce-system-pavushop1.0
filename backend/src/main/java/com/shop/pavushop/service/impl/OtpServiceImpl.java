package com.shop.pavushop.service.impl;

import java.time.Duration;
import java.util.Random;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.shop.pavushop.service.OtpService;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public String generateOtp() {

	    Random random = new Random();
	    return String.valueOf(
	            100000 + random.nextInt(900000)
	    );

	}

	@Override
	public void saveOtp(String email, String otp) {

	    redisTemplate.opsForValue().set(
	            "otp:"+email,
	            otp,
	            Duration.ofMinutes(5)
	    );

	}

	@Override
	public boolean verifyOtp(String email, String otp) {

	    Object value = redisTemplate.opsForValue()
	            .get("otp:"+email);

	    if(value==null){
	        return false;

	    }
	    return otp.equals(value.toString());
	}

	@Override
	public void deleteOtp(String email) {

	    redisTemplate.delete("otp:"+email);
	}
}
