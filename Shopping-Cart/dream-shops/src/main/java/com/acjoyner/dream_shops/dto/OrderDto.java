package com.acjoyner.dream_shops.dto;

import com.acjoyner.dream_shops.enums.OrderStatus;
import com.acjoyner.dream_shops.model.OrderItem;
import com.acjoyner.dream_shops.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrderDto {

    private Long orderId;
    private Long userId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDto> items;

}
