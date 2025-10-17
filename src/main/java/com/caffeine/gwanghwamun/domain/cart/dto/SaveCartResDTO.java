package com.caffeine.gwanghwamun.domain.cart.dto;

import com.caffeine.gwanghwamun.domain.cart.entity.Cart;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SaveCartResDTO(
		UUID cartId,
		Long userId,
		UUID storeId,
		UUID menuId,
		List<CartItemOptionResDTO> cartItemOptionList,
		Integer quantity,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {
	public SaveCartResDTO(Cart cart, List<CartItemOptionResDTO> cartItemOptionList) {
		this(
				cart.getCartId(),
				cart.getUser().getUserId(),
				cart.getStore().getStoreId(),
				cart.getMenu().getMenuId(),
				cartItemOptionList,
				cart.getQuantity(),
				cart.getCreateAt(),
				cart.getLastUpdatedAt());
	}
}
