package com.shop.pavushop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.pavushop.entity.CartItemEntity;
import com.shop.pavushop.entity.UserInfoEntity;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {
    List<CartItemEntity> findByUser(UserInfoEntity user);
    void deleteByUser(UserInfoEntity user);
}
