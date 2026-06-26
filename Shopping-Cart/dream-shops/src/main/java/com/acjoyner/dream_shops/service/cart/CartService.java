package com.acjoyner.dream_shops.service.cart;

import com.acjoyner.dream_shops.exceptions.ResourceNotFoundException;
import com.acjoyner.dream_shops.model.Cart;
import com.acjoyner.dream_shops.model.CartItem;
import com.acjoyner.dream_shops.repository.CartItemRepository;
import com.acjoyner.dream_shops.repository.CartRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Data
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Override
    @Transactional
    public void clearCart(Long id) {
        cartItemRepository.deleteAllByCartId(id);
        cartRepository.deleteById(id);
    }


    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    @Transactional
    public Long initializeNewCart(){
        Cart newCart = new Cart();
        return cartRepository.save(newCart).getId();
    }

    @Override
    public void addItem(Cart cart, CartItem item) {
        cart.getItems().add(item);
        item.setCart(cart);
        updateTotalAmount(cart);
    }

    @Override
    public void removeItem(Cart cart, CartItem item) {
        cart.getItems().remove(item);
        item.setCart(null);
        updateTotalAmount(cart);
    }

    @Override
    public void updateTotalAmount(Cart cart) {
        cart.setTotalAmount(cart.getItems().stream().map(item -> {
            BigDecimal unitPrice = item.getUnitPrice();
            if (unitPrice == null) {
                return BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));
    }
}
