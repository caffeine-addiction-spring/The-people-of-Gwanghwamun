package com.caffeine.gwanghwamun.domain.cart.dto;

import com.caffeine.gwanghwamun.domain.cart.entity.Cart;
import java.util.UUID;

public record CartResDTO(
		UUID cartId, Long userId, UUID storeId, UUID menuId, UUID menuOptionId, Integer quantity) {
	public CartResDTO(Cart cart) {
		this(
				cart.getCartId(),
				cart.getUser().getUserId(),
				cart.getStore().getStoreId(),
				cart.getMenu().getMenuId(),
				cart.getMenuOption() != null ? cart.getMenuOption().getMenuOptionId() : null,
				cart.getQuantity());
	}
}
