package com.shop.pavushop.controller.user;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.pavushop.dto.CheckoutDTO;
import com.shop.pavushop.entity.CartItemEntity;
import com.shop.pavushop.entity.Order;
import com.shop.pavushop.mapper.CheckoutMapper;
import com.shop.pavushop.service.user.ShoppingCartService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class ShoppingCartController {
	private final CheckoutMapper checkoutMapper;

    @Autowired
    private ShoppingCartService cartService;

    @GetMapping
    public ResponseEntity<Collection<CartItemEntity>> getCartItems(Principal principal) {
        List<CartItemEntity> items = cartService.getCartItems(principal);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addToCart(@PathVariable Integer productId, Principal principal) {
        cartService.add(productId, principal);
        return ResponseEntity.ok("thêm thành công"); 
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeItem(@PathVariable Integer id) {
        cartService.remove(id);
        return ResponseEntity.ok("xoá thành công");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(Principal principal) {
        cartService.clear(principal);
        return ResponseEntity.ok("xoá hết thành công");
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount(Principal principal) {
        int count = cartService.getCount(principal);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotal(Principal principal) {
        double total = cartService.totalPrice(principal);
        return ResponseEntity.ok(total);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Integer> checkout(@RequestBody CheckoutDTO checkoutDTO, Principal principal) {
    	Order order = checkoutMapper.convertToEntity(checkoutDTO);
        int orderId = cartService.checkedOut(order, principal);
        return ResponseEntity.ok(orderId);
    }
}
