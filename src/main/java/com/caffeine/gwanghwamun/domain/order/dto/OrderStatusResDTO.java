package com.caffeine.gwanghwamun.domain.order.dto;

import com.caffeine.gwanghwamun.domain.order.entity.Order;
import com.caffeine.gwanghwamun.domain.order.entity.OrderStatus;

import java.util.UUID;

public record OrderStatusResDTO(
        UUID orderId,
        OrderStatus orderStatus
) {
    public OrderStatusResDTO(Order order) {
        this(
                order.getOrderId(),
                order.getOrderStatus()
        );
    }
}
