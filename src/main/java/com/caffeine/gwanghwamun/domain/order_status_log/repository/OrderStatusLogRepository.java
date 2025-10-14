package com.caffeine.gwanghwamun.domain.order_status_log.repository;

import com.caffeine.gwanghwamun.domain.order_status_log.entity.OrderStatusLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusLogRepository extends JpaRepository<OrderStatusLog, UUID> {}
