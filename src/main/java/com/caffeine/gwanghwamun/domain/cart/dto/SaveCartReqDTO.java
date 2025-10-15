package com.caffeine.gwanghwamun.domain.cart.dto;

import com.caffeine.gwanghwamun.domain.cart.entity.CartMode;
import jakarta.validation.constraints.Min;
import java.util.UUID;

public record SaveCartReqDTO(
		UUID storeId,
		UUID menuId,
		UUID menuOptionId,
		@Min(value = 0, message = "수량은 0 이상이어야 합니다.") Integer quantity,
		CartMode cartMode) {}
