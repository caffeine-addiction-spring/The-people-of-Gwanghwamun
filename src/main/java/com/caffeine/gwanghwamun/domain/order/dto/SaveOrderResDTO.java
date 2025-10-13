package com.caffeine.gwanghwamun.domain.order.dto;

import com.caffeine.gwanghwamun.domain.order.entity.OrderStatus;
import java.util.UUID;

public record SaveOrderResDTO(UUID orderId, OrderStatus status, int totalPrice) {}
