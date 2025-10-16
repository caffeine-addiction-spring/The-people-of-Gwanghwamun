package com.caffeine.gwanghwamun.domain.order.dto;

import java.util.UUID;

public record OrderItemOptionResDTO(
    UUID orderItemOptionId, String orderItemOptionName, Integer price) {
}
