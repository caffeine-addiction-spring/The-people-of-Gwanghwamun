package com.caffeine.gwanghwamun.domain.order.repository;

import com.caffeine.gwanghwamun.domain.order.entity.OrderItem;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
  List<OrderItem> findByOrder_OrderIdAndDeletedDateIsNull(UUID orderId);
}
