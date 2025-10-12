package com.caffeine.gwanghwamun.domain.order.order_items.repository;

import com.caffeine.gwanghwamun.domain.order.order_items.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
