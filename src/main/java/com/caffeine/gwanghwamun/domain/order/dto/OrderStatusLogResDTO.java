package com.caffeine.gwanghwamun.domain.order.dto;

import com.caffeine.gwanghwamun.domain.order.entity.OrderStatus;
import com.caffeine.gwanghwamun.domain.order.entity.OrderStatusLog;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderStatusLogResDTO(
		UUID orderStatusLogId,
		UUID orderId,
		Long userId,
		UUID storeId,
		OrderStatus existingState,
		OrderStatus currentState,
		String reason,
		LocalDateTime createAt) {
	public OrderStatusLogResDTO(OrderStatusLog orderStatusLog) {
		this(
				orderStatusLog.getOrderStatusLogId(),
				orderStatusLog.getOrder().getOrderId(),
				orderStatusLog.getUser().getUserId(),
				orderStatusLog.getStore().getStoreId(),
				orderStatusLog.getExistingState(),
				orderStatusLog.getCurrentState(),
				orderStatusLog.getReason(),
				orderStatusLog.getCreateAt());
	}
}
