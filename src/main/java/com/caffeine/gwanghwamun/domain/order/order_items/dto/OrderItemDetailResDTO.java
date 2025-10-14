package com.caffeine.gwanghwamun.domain.order.order_items.dto;

import com.caffeine.gwanghwamun.domain.order.order_items.entity.OrderItem;

import java.util.List;
import java.util.UUID;

public record OrderItemDetailResDTO(
    UUID orderItemId,
    UUID menuId,
    String menuName,
    List<OrderItemOptionResDto> orderItemOptionList,
    Integer quantity,
    Integer price
) {
  public OrderItemDetailResDTO(
      OrderItem orderItem,
      List<OrderItemOptionResDto> orderItemOptionList,
      Integer itemPrice
  ) {
    this(
        orderItem.getOrderItemId(),
        orderItem.getMenu().getMenuId(),
        orderItem.getMenu().getName(),
        orderItemOptionList,
        orderItem.getQuantity(),
        itemPrice
    );
  }

}
