package com.caffeine.gwanghwamun.domain.order.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.cart.repository.CartRepository;
import com.caffeine.gwanghwamun.domain.order.dto.*;
import com.caffeine.gwanghwamun.domain.order.entity.Order;
import com.caffeine.gwanghwamun.domain.order.entity.OrderItem;
import com.caffeine.gwanghwamun.domain.order.entity.OrderStatus;
import com.caffeine.gwanghwamun.domain.order.entity.OrderStatusLog;
import com.caffeine.gwanghwamun.domain.order.repository.OrderItemRepository;
import com.caffeine.gwanghwamun.domain.order.repository.OrderRepository;
import com.caffeine.gwanghwamun.domain.order.repository.OrderStatusLogRepository;
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
	private final UserRepository userRepository;
	private final CartRepository cartRepository;
	private final OrderItemRepository orderItemRepository;
	private final OrderItemService orderItemService;
	private final OrderStatusLogRepository orderStatusLogRepository;

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

		User user =
				userRepository
						.findById(userId)
						.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		Store store =
				storeRepository
						.findActiveById(req.storeId())
						.orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

		int totalPrice = 0;

		Order order =
				Order.builder()
						.store(store)
						.user(user)
						.orderStatus(OrderStatus.ORDER_WAITING)
						.deliveryAddress(req.address())
						.totalPrice(totalPrice)
						.deliveryContent(req.deliveryContent())
						.requests(req.requests())
						.build();

		orderRepository.save(order);

		//    if (req.cartMode() == CartMode.CART) {
		//      List<Cart> cartItemList = cartRepository.findByUserAndStoreAndDeletedDateIsNull(user,
		// store);
		//      cartItemList.stream().map()
		//    } else {
		List<OrderItemListResDTO> orderItemResList =
				orderItemService.saveOrderItem(req.menuItemList(), order);
		//    }
		totalPrice = orderItemResList.stream().mapToInt(OrderItemListResDTO::totalPrice).sum();
		if (totalPrice < store.getMinDeliveryPrice()) {
			throw new CustomException(ErrorCode.LOW_MIN_DELEVERY_PRICE);
		}
		totalPrice += store.getDeliveryTip();
		order.updateTotalPrice(totalPrice);

		saveOrderStatusLog(order, user, null, "");

		return new SaveOrderResDTO(order);
	}

	@Transactional
	public OrderStatusResDTO cancelOrder(User user, UUID orderId, OrderCancelReqDTO req) {

		Order order =
				orderRepository
						.findById(orderId)
						.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		validateOrderAccess(user, order);
		validateOrderTimeLimit(order);

		OrderStatus existingState = order.getOrderStatus();

		order.cancel();

		saveOrderStatusLog(order, user, existingState, req.reason());

		return new OrderStatusResDTO(order);
	}

	@Transactional
	public OrderStatusResDTO acceptOrder(User user, UUID orderId) {
		Order order =
				orderRepository
						.findById(orderId)
						.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		validateOrderAccess(user, order);

		OrderStatus existingState = order.getOrderStatus();

		order.accept();

		saveOrderStatusLog(order, user, existingState, "주문 수락");

		return new OrderStatusResDTO(order);
	}

	@Transactional
	public OrderStatusResDTO rejectOrder(User user, UUID orderId) {
		Order order =
				orderRepository
						.findById(orderId)
						.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		validateOrderAccess(user, order);

		OrderStatus existingState = order.getOrderStatus();

		order.reject();

		saveOrderStatusLog(order, user, existingState, "주문 거절");

		return new OrderStatusResDTO(order);
	}

	@Transactional
	public OrderStatusResDTO completeCooking(User user, UUID orderId) {
		Order order =
				orderRepository
						.findById(orderId)
						.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		validateOrderAccess(user, order);

		OrderStatus existingState = order.getOrderStatus();

		order.completeCooking();

		saveOrderStatusLog(order, user, existingState, "조리완료");

		return new OrderStatusResDTO(order);
	}

	@Transactional
	public OrderStatusResDTO completeDelivery(User user, UUID orderId) {
		Order order =
				orderRepository
						.findById(orderId)
						.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		validateOrderAccess(user, order);

		OrderStatus existingState = order.getOrderStatus();

		order.completeDelivery();

		saveOrderStatusLog(order, user, existingState, "배달완료");

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

	private void saveOrderStatusLog(
			Order order, User user, OrderStatus existingState, String reason) {
		OrderStatusLog log =
				OrderStatusLog.builder()
						.order(order)
						.user(user)
						.existingState(existingState)
						.store(order.getStore())
						.currentState(order.getOrderStatus())
						.reason(reason)
						.build();

		orderStatusLogRepository.save(log);
	}
}
