package com.shop.pavushop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.pavushop.entity.UserInfoEntity;

@Repository
public interface UserRepository extends JpaRepository<UserInfoEntity, Integer> {

    Optional<UserInfoEntity> findByEmail(String email);
    Optional<UserInfoEntity> findByUsername(String username);
    
}