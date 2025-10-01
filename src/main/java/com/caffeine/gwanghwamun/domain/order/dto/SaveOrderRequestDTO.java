package com.caffeine.gwanghwamun.domain.order.dto;

import java.util.HashMap;
import java.util.UUID;

public record SaveOrderRequestDTO(
        UUID storeId,
        HashMap<UUID, Integer> menuItems,
        String address,
        Object paymentMethod
) {
}