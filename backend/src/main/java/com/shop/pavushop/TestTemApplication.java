package com.shop.pavushop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.shop.pavushop.config.jwtAuth.RSAKeyRecord;
@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
public class TestTemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestTemApplication.class, args);
	}

}
