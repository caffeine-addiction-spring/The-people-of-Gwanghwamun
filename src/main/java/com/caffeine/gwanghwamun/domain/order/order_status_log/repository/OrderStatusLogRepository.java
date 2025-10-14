package com.caffeine.gwanghwamun.domain.order.order_status_log.repository;

import com.caffeine.gwanghwamun.domain.order.order_status_log.entity.OrderStatusLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderStatusLogRepository extends JpaRepository<OrderStatusLog, UUID> {

  Page<OrderStatusLog> findAllByUser_UserId(Long userId, Pageable pageable);

  Page<OrderStatusLog> findAllByStore_StoreId(UUID storeId, Pageable pageable);

  Page<OrderStatusLog> findAllByOrder_OrderId(UUID orderId, Pageable pageable);
}