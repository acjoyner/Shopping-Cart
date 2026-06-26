package com.acjoyner.dream_shops.repository;

import com.acjoyner.dream_shops.model.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface CartRepository  extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserId(Long userId);
}
