package com.shop.pavushop.service.user.impl;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.pavushop.entity.CartItemEntity;
import com.shop.pavushop.entity.Order;
import com.shop.pavushop.entity.OrderDetail;
import com.shop.pavushop.entity.Product;
import com.shop.pavushop.entity.UserInfoEntity;
import com.shop.pavushop.enums.OrderStatus;
import com.shop.pavushop.repository.CartItemRepository;
import com.shop.pavushop.repository.OrderDetailRepository;
import com.shop.pavushop.repository.OrderRepository;
import com.shop.pavushop.repository.ProductRepository;
import com.shop.pavushop.repository.UserInfoRepo;
import com.shop.pavushop.service.EmailService;
import com.shop.pavushop.service.user.ShoppingCartService;

import jakarta.transaction.Transactional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Autowired
    private EmailService emailService;

    @Override
    public void add(Integer productId, Principal principal) {
        Product product = productRepository.findById(productId).orElse(null);
        UserInfoEntity user = userInfoRepo.findByUsername(principal.getName()).orElse(null);

        if (product != null && user != null) {
            List<CartItemEntity> existingItems = cartItemRepository.findByUser(user);
            CartItemEntity existingItem = existingItems.stream()
                    .filter(item -> item.getProduct().getProductId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + 1);
                existingItem.setTotalPrice(existingItem.getUnitPrice() * existingItem.getQuantity());
                cartItemRepository.save(existingItem);
            } else {
                CartItemEntity newItem = new CartItemEntity();
                newItem.setProduct(product);
                newItem.setUser(user);
                newItem.setQuantity(1);
                newItem.setUnitPrice(product.getPrice());
                newItem.setTotalPrice(product.getPrice());
                cartItemRepository.save(newItem);
            }
        }
    }

    @Override
    public List<CartItemEntity> getCartItems(Principal principal) {
        UserInfoEntity user = userInfoRepo.findByUsername(principal.getName()).orElse(null);
        return cartItemRepository.findByUser(user);
    }

    @Override
    public int getCount(Principal principal) {
        return getCartItems(principal).stream()
                .mapToInt(CartItemEntity::getQuantity)
                .sum();
    }

    @Override
    public void remove(Integer cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void clear(Principal principal) {
        UserInfoEntity user = userInfoRepo.findByUsername(principal.getName()).orElse(null);
        cartItemRepository.deleteByUser(user);
    }

    @Override
    public double totalPrice(Principal principal) {
        return getCartItems(principal).stream()
                .mapToDouble(item -> {
                    double price = item.getUnitPrice() * item.getQuantity();
                    double discount = item.getProduct().getDiscount();
                    return price - (price * discount / 100);
                })
                .sum();
    }

    @Override
    @Transactional
    public int checkedOut(Order order, Principal principal) {
        UserInfoEntity user = userInfoRepo.findByUsername(principal.getName())
                .orElse(null);

        List<CartItemEntity> cartItems = cartItemRepository.findByUser(user);

        double total = cartItems.stream()
        .mapToDouble(item -> {
            double price = item.getUnitPrice() * item.getQuantity();
            double discount = item.getProduct().getDiscount();
            return price - (price * discount / 100);
        })
        .sum();

        order.setUserInfoEntity(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING.getValue());
        order.setTotalPrice(total);
        orderRepository.save(order);

        for (CartItemEntity item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getUnitPrice());

            double price = item.getUnitPrice() * item.getQuantity();
            double discount = item.getProduct().getDiscount();
            detail.setTotalPrice(price - (price * discount / 100));

            orderDetailRepository.save(detail);
        }
        emailService.sendOrderSuccess(order);
        emailService.sendNewOrderToAdmin(order);
        cartItemRepository.deleteByUser(user);
        return order.getOrderId();
    }

}
