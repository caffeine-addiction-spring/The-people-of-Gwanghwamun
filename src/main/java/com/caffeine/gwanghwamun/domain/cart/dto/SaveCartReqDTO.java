package com.caffeine.gwanghwamun.domain.cart.dto;

import java.util.UUID;

public record SaveCartReqDTO(UUID storeId, UUID menuId, UUID menuOptionId, int quantity) {}
