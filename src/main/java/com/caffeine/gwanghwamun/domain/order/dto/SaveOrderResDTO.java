package com.caffeine.gwanghwamun.domain.order.dto;

import com.caffeine.gwanghwamun.domain.order.entity.Order;
import com.caffeine.gwanghwamun.domain.order.entity.OrderStatus;

import java.util.UUID;

public record SaveOrderResDTO(
    UUID orderId,
    OrderStatus status,
    Integer totalPrice
) {
  public SaveOrderResDTO(Order order) {
    this(
        order.getOrderId(),
        order.getOrderStatus(),
        order.getTotalPrice()
    );
  }
}
