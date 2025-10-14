package com.caffeine.gwanghwamun.domain.order.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.cart.repository.CartRepository;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuRepository;
import com.caffeine.gwanghwamun.domain.order.dto.*;
import com.caffeine.gwanghwamun.domain.order.entity.Order;
import com.caffeine.gwanghwamun.domain.order.entity.OrderStatus;
import com.caffeine.gwanghwamun.domain.order.repository.OrderRepository;
import com.caffeine.gwanghwamun.domain.order_items.dto.OrderItemResDTO;
import com.caffeine.gwanghwamun.domain.order_items.entity.OrderItem;
import com.caffeine.gwanghwamun.domain.order_items.repository.OrderItemRepository;
import com.caffeine.gwanghwamun.domain.order_status_log.repository.OrderStatusLogRepository;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.store.repository.StoreRepository;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.entity.UserRoleEnum;
import com.caffeine.gwanghwamun.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final StoreRepository storeRepository;
	private final MenuRepository menuRepository;
	private final UserRepository userRepository;
	private final CartRepository cartRepository;
	private final OrderStatusLogRepository orderStatusLogRepository;
	private final OrderItemRepository orderItemRepository;

	public Page<OrderListResDTO> findOrderList(Long userId, Pageable pageable) {

		Page<Order> orderPage = orderRepository.findByUser_UserIdAndDeletedDateIsNull(userId, pageable);

		return orderPage.map(OrderListResDTO::new);
	}

	public OrderResDTO findOrder(Long userId, UUID orderId) {

		userRepository
				.findById(userId)
				.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		Order order =
				orderRepository
						.findById(orderId)
						.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		List<OrderItem> orderItemList =
				orderItemRepository.findByOrder_OrderIdAndDeletedDateIsNull(orderId);

		List<OrderItemResDTO> orderItemResDTOList =
				orderItemList.stream().map(OrderItemResDTO::new).toList();

		return new OrderResDTO(order, orderItemResDTOList);
	}

	@Transactional
	public SaveOrderResDTO saveOrder(SaveOrderReqDTO req, Long userId) {

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

	public OrderStatusResDTO cancelOrder(User user, UUID orderId, OrderCancelReqDTO req) {

		Order order =
				orderRepository
						.findById(orderId)
						.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		validateOrderAccess(user, order);
		validateOrderTimeLimit(order);

		order.cancel();

		return new OrderStatusResDTO(order);
	}

	public OrderStatusResDTO acceptOrder(User user, UUID orderId) {
		Order order =
				orderRepository
						.findById(orderId)
						.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		validateOrderAccess(user, order);

		order.accept();

		return new OrderStatusResDTO(order);
	}

	public OrderStatusResDTO rejectOrder(User user, UUID orderId) {
		Order order =
				orderRepository
						.findById(orderId)
						.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		validateOrderAccess(user, order);

		order.reject();

		return new OrderStatusResDTO(order);
	}

	public OrderStatusResDTO completeCooking(User user, UUID orderId) {
		Order order =
				orderRepository
						.findById(orderId)
						.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		validateOrderAccess(user, order);

		order.completeCooking();

		return new OrderStatusResDTO(order);
	}

	public OrderStatusResDTO completeDelivery(User user, UUID orderId) {
		Order order =
				orderRepository
						.findById(orderId)
						.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		validateOrderAccess(user, order);

		order.completeDelivery();

		return new OrderStatusResDTO(order);
	}

	private void validateOrderTimeLimit(Order order) {
		LocalDateTime limitTime = order.getCreateAt().plusMinutes(5);
		if (LocalDateTime.now().isAfter(limitTime)) {
			throw new CustomException(ErrorCode.ORDER_TIME_EXPIRED);
		}
	}

	private void validateOrderAccess(User user, Order order) {
		if (user.getRole() == UserRoleEnum.CUSTOMER) {
			if (!Objects.equals(order.getUser().getUserId(), user.getUserId())) {
				throw new CustomException(ErrorCode.UNAUTHORIZED_ORDER_ACCESS);
			}
		}

		if (user.getRole() == UserRoleEnum.OWNER) {
			if (!Objects.equals(order.getStore().getUser().getUserId(), user.getUserId())) {
				throw new CustomException(ErrorCode.UNAUTHORIZED_STORE_ACCESS);
			}
		}
	}
}
