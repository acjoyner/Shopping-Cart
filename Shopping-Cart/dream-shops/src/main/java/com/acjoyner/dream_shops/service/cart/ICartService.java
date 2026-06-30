package com.acjoyner.dream_shops.service.cart;

import com.acjoyner.dream_shops.model.Cart;
import com.acjoyner.dream_shops.model.CartItem;
import com.acjoyner.dream_shops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    void addItem(Cart cart, CartItem item);
    void removeItem(Cart cart, CartItem item);
    void updateTotalAmount(Cart cart);

    Cart getCartByUserId(Long userId);

}
