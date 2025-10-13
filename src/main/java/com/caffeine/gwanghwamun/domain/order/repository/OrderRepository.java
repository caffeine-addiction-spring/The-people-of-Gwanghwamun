package com.caffeine.gwanghwamun.domain.order.repository;

import com.caffeine.gwanghwamun.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findByUser_UserIdAndDeletedDateIsNull(Long userId, Pageable pageable);
}
