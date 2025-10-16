package com.caffeine.gwanghwamun.domain.order.repository;

import com.caffeine.gwanghwamun.domain.order.entity.OrderItemOption;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemOptionRepository extends JpaRepository<OrderItemOption, UUID> {}
