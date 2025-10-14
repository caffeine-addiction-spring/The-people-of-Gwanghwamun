package com.caffeine.gwanghwamun.domain.order.dto;

import com.caffeine.gwanghwamun.domain.order.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record findOrderResDTO(
    UUID orderId, UUID storeId, String items, OrderStatus status, LocalDateTime createdAt
) {

}
