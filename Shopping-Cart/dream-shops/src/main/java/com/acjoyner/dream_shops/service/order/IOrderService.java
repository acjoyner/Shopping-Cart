package com.acjoyner.dream_shops.service.order;

import com.acjoyner.dream_shops.dto.OrderDto;

import java.util.List;

public interface IOrderService {
    OrderDto placeOrder(Long userId);
    List<OrderDto> getUserOrders(Long userId);
    OrderDto getOrder(Long orderId);
}
