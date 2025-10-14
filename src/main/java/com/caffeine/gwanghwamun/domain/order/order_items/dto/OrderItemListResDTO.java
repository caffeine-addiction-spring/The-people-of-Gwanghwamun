package com.caffeine.gwanghwamun.domain.order.order_items.dto;

import com.caffeine.gwanghwamun.domain.order.order_items.entity.OrderItem;
import com.caffeine.gwanghwamun.domain.order.order_items.order_item_options.entity.OrderItemOption;
import java.util.List;

public record OrderItemListResDTO(
		OrderItem orderItem, List<OrderItemOption> orderItemOptionList, Integer totalPrice) {}
