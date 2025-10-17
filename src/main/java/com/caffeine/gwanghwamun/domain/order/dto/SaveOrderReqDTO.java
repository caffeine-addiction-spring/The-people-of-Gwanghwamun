package com.caffeine.gwanghwamun.domain.order.dto;

import com.caffeine.gwanghwamun.domain.order.entity.CartMode;
import java.util.List;
import java.util.UUID;

public record SaveOrderReqDTO(
		UUID storeId,
		CartMode cartMode,
		List<OrderMenuItemReqDTO> menuItemList,
		String address,
		Object paymentMethod,
		String deliveryContent,
		String requests) {}
