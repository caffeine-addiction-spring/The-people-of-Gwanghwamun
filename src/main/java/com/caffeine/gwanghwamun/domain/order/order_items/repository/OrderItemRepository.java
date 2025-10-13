package com.caffeine.gwanghwamun.domain.order.order_items.repository;

import com.caffeine.gwanghwamun.domain.order.order_items.entity.OrderItem;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {}
