package com.caffeine.gwanghwamun.domain.cart.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record SaveCartResDTO(
		UUID cartId,
		UUID userId,
		UUID storeId,
		UUID menuId,
		UUID menuOptionId,
		int quantity,
		LocalDateTime createdDate,
		LocalDateTime modifiedDate) {}
