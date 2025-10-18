package com.caffeine.gwanghwamun.domain.order.dto;

import com.caffeine.gwanghwamun.domain.order.entity.OrderItem;
import com.caffeine.gwanghwamun.domain.order.entity.OrderItemOption;
import java.util.List;
import java.util.UUID;

public record OrderItemListResDTO(
		OrderItem orderItem, List<OrderItemOption> orderItemOptionList, Integer totalPrice) {
	public static record OrderItemDetailResDTO(
			UUID orderItemId,
			UUID menuId,
			String menuName,
			List<OrderItemOptionResDTO> orderItemOptionList,
			Integer quantity,
			Integer price) {
		public OrderItemDetailResDTO(
				OrderItem orderItem, List<OrderItemOptionResDTO> orderItemOptionList, Integer itemPrice) {
			this(
					orderItem.getOrderItemId(),
					orderItem.getMenu().getMenuId(),
					orderItem.getMenuName(),
					orderItemOptionList,
					orderItem.getQuantity(),
					itemPrice);
		}
	}
}
