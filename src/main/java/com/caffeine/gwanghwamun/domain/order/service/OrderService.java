package com.caffeine.gwanghwamun.domain.order.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.cart.repository.CartRepository;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuRepository;
import com.caffeine.gwanghwamun.domain.order.dto.SaveOrderReqDTO;
import com.caffeine.gwanghwamun.domain.order.dto.SaveOrderResDTO;
import com.caffeine.gwanghwamun.domain.order.entity.Order;
import com.caffeine.gwanghwamun.domain.order.entity.OrderStatus;
import com.caffeine.gwanghwamun.domain.order.order_status_log.repository.OrderStatusLogRepository;
import com.caffeine.gwanghwamun.domain.order.repository.OrderRepository;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final StoreRepository storeRepository;
	private final MenuRepository menuRepository;
	private final CartRepository cartRepository;
	private final OrderStatusLogRepository orderStatusLogRepository;

	public Order findOrder(UUID orderId) {
		return orderRepository
				.findById(orderId)
				.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
	}

	@Transactional
	public SaveOrderResDTO saveOrder(SaveOrderReqDTO req, UUID userId) {

		Store store =
				storeRepository
						.findActiveById(req.storeId())
						.orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

		Order order =
				Order.builder()
						.store(store)
						.deliveryAddress(req.address())
						.orderStatus(OrderStatus.DELIVERING)
						.build();

		int totalPrice = 0;

		return null;
	}
}
