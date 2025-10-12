package com.caffeine.gwanghwamun.domain.order.order_items.order_item_options.repository;

import com.caffeine.gwanghwamun.domain.order.order_items.order_item_options.entity.OrderItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemOptionRepository extends JpaRepository<OrderItemOption, UUID> {
}
