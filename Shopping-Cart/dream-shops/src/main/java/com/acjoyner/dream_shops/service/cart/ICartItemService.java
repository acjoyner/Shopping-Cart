package com.acjoyner.dream_shops.service.cart;

import com.acjoyner.dream_shops.model.CartItem;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long itemId);
    void updateItemQuantity(Long cartId, Long itemId, int quantity);

    @Transactional(readOnly = true)
    CartItem getCartItem(Long cartId, Long itemId);
}
