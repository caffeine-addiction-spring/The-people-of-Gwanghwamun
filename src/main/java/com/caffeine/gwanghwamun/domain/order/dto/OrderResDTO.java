package com.caffeine.gwanghwamun.domain.order.dto;

import com.caffeine.gwanghwamun.domain.order.entity.Order;
import com.caffeine.gwanghwamun.domain.order.entity.OrderStatus;
import com.caffeine.gwanghwamun.domain.order.order_items.dto.OrderItemResDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResDTO(
		UUID orderId,
		Long userId,
		UUID storeId,
		String storeName,
		List<OrderItemResDTO> items,
		int totalPrice,
		String deliveryAddress,
		String requests,
		OrderStatus status,
		LocalDateTime createdAt) {
	public OrderResDTO(Order order, List<OrderItemResDTO> orderItemResDTOList) {
		this(
				order.getOrderId(),
				order.getUser().getUserId(),
				order.getStore().getStoreId(),
				order.getStore().getName(),
				orderItemResDTOList,
				order.getTotalPrice(),
				order.getDeliveryAddress(),
				order.getRequests(),
				order.getOrderStatus(),
				order.getCreateAt());
	}
}
