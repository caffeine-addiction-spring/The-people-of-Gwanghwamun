package com.caffeine.gwanghwamun.domain.order.order_items.dto;

import java.util.UUID;

public record OrderItemOptionResDto(
    UUID orderItemOptionId,
    String orderItemOptionName,
    Integer price
) {
}
