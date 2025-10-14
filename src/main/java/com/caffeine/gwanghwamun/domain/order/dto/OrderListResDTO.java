package com.caffeine.gwanghwamun.domain.order.dto;

import com.caffeine.gwanghwamun.domain.order.entity.Order;
import com.caffeine.gwanghwamun.domain.order.entity.OrderStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderListResDTO(
		UUID orderId,
		Long userId,
		UUID storeId,
		String storeName,
		int totalPrice,
		String deliveryAddress,
		String requests,
		OrderStatus status,
		LocalDateTime createdAt) {
	public OrderListResDTO(Order order) {
		this(
				order.getOrderId(),
				order.getUser().getUserId(),
				order.getStore().getStoreId(),
				order.getStore().getName(),
				order.getTotalPrice(),
				order.getDeliveryAddress(),
				order.getRequests(),
				order.getOrderStatus(),
				order.getCreateAt());
	}
}
