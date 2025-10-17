package com.caffeine.gwanghwamun.domain.cart.dto;

import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

public record SaveCartReqDTO(
		UUID storeId,
		UUID menuId,
		List<UUID> menuOptionIdList,
		@Min(value = 0, message = "수량은 0 이상이어야 합니다.") Integer quantity) {}
