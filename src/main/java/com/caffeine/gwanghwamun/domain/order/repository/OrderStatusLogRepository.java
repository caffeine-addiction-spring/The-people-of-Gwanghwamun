package com.caffeine.gwanghwamun.domain.order.repository;

import com.caffeine.gwanghwamun.domain.order.entity.OrderStatusLog;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusLogRepository extends JpaRepository<OrderStatusLog, UUID> {

  Page<OrderStatusLog> findAllByUser_UserId(Long userId, Pageable pageable);

  Page<OrderStatusLog> findAllByStore_StoreId(UUID storeId, Pageable pageable);

  Page<OrderStatusLog> findAllByOrder_OrderId(UUID orderId, Pageable pageable);
}
