package com.caffeine.gwanghwamun.domain.order.dto;

import com.caffeine.gwanghwamun.domain.cart.entity.CartMode;
import com.caffeine.gwanghwamun.domain.order.order_items.dto.OrderMenuItemReqDTO;
import java.util.List;
import java.util.UUID;

public record SaveOrderReqDTO(
		UUID storeId,
		List<OrderMenuItemReqDTO> menuItemList,
		String address,
		Object paymentMethod,
		CartMode cartMode,
		String deliveryContent,
		String requests) {}
