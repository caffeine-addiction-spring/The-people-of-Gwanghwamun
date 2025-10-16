package com.caffeine.gwanghwamun.domain.cart.dto;

import com.caffeine.gwanghwamun.domain.cart.entity.Cart;
import com.caffeine.gwanghwamun.domain.cart.entity.CartItemOption;
import java.util.List;
import java.util.UUID;

public record CartUpdateResDTO(
		UUID cartId,
		Long userId,
		UUID storeId,
		UUID menuId,
		List<CartItemOption> cartItemOptionList,
		Integer quantity) {
	public CartUpdateResDTO(Cart cart, List<CartItemOption> cartItemOptionList) {
		this(
				cart.getCartId(),
				cart.getUser().getUserId(),
				cart.getStore().getStoreId(),
				cart.getMenu().getMenuId(),
				cartItemOptionList,
				cart.getQuantity());
	}
}
