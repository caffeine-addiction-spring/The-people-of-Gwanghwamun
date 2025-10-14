package com.caffeine.gwanghwamun.domain.order.dto;

import com.caffeine.gwanghwamun.domain.cart.entity.CartMode;
import com.caffeine.gwanghwamun.domain.order.order_items.dto.OrderMenuItemReqDto;

import java.util.List;
import java.util.UUID;

public record SaveOrderReqDTO(
    UUID storeId,
    List<OrderMenuItemReqDto> menuItemList,
    String address,
    Object paymentMethod,
    CartMode cartMode
) {
}
