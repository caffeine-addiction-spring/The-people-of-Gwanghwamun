package com.caffeine.gwanghwamun.domain.order.order_items.order_item_options.repository;

import com.caffeine.gwanghwamun.domain.order.order_items.order_item_options.entity.OrderItemOption;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemOptionRepository extends JpaRepository<OrderItemOption, UUID> {}
