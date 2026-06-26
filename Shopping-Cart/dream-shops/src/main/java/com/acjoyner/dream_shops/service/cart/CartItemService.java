package com.acjoyner.dream_shops.service.cart;

import com.acjoyner.dream_shops.exceptions.ResourceNotFoundException;
import com.acjoyner.dream_shops.model.Cart;
import com.acjoyner.dream_shops.model.CartItem;
import com.acjoyner.dream_shops.model.Product;
import com.acjoyner.dream_shops.repository.CartItemRepository;
import com.acjoyner.dream_shops.repository.CartRepository;
import com.acjoyner.dream_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;


    @Override
    @Transactional
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // 1. Get the cart
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());
        // 4. If yes, increase the quantity with requested quantity,
        if(cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cartService.addItem(cart, cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
        // 2. Get the product
        // 3. Check if the product is already in the cart

        // 5. if no, initiate a new cart item entry
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, itemId);
        cartService.removeItem(cart, itemToRemove);
        cartRepository.save(cart);

    }

    @Override
    @Transactional
    public void updateItemQuantity(Long cartId, Long itemId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        CartItem item = getCartItem(cartId, itemId);
        item.setQuantity(quantity);
        item.setUnitPrice(item.getProduct().getPrice());
        item.setTotalPrice();
        cartItemRepository.save(item);
        cartService.updateTotalAmount(cart);
        cartRepository.save(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartItem getCartItem(Long cartId, Long itemId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst().orElseThrow(()->new ResourceNotFoundException("Cart item not found"));

    }
}
