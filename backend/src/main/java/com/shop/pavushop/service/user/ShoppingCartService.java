package com.shop.pavushop.service.user;

import java.security.Principal;
import java.util.List;

import com.shop.pavushop.entity.CartItemEntity;
import com.shop.pavushop.entity.Order;

public interface ShoppingCartService {
	
    void add(Integer productId, Principal principal);
    List<CartItemEntity> getCartItems(Principal principal);
    int getCount(Principal principal);
    void remove(Integer cartItemId);
    void clear(Principal principal);
    double totalPrice(Principal principal);
    int checkedOut(Order order, Principal principal);
}
