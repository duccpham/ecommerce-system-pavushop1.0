package com.shop.pavushop.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.pavushop.entity.UserInfoEntity;
import com.shop.pavushop.repository.UserInfoRepo;
import com.shop.pavushop.service.admin.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	UserInfoRepo userInfoRepo;

	@Override
	public List<UserInfoEntity> customer() {
		return userInfoRepo.findAll();
	}

	

}
