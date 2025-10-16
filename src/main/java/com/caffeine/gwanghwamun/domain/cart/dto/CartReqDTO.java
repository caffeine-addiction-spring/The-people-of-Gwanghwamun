package com.caffeine.gwanghwamun.domain.cart.dto;

import java.util.UUID;

public record CartReqDTO(UUID cartId, UUID menuId, UUID menuOptionId, Integer quantity) {}
