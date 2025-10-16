package com.caffeine.gwanghwamun.domain.order.dto;

import com.caffeine.gwanghwamun.domain.order.entity.OrderItem;

import java.util.UUID;

public record OrderItemResDTO(UUID orderItemId, UUID menuId, String menuName, Integer quantity) {
  public OrderItemResDTO(OrderItem orderItem) {
    this(
        orderItem.getOrderItemId(),
        orderItem.getMenu().getMenuId(),
        orderItem.getMenu().getName(),
        orderItem.getQuantity());
  }
}
