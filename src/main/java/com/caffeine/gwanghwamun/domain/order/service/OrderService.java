package com.caffeine.gwanghwamun.domain.order.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.cart.repository.CartRepository;
import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import com.caffeine.gwanghwamun.domain.menu.entity.MenuOption;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuOptionRepository;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuRepository;
import com.caffeine.gwanghwamun.domain.order.dto.*;
import com.caffeine.gwanghwamun.domain.order.entity.Order;
import com.caffeine.gwanghwamun.domain.order.entity.OrderStatus;
import com.caffeine.gwanghwamun.domain.order.order_items.dto.OrderItemListResDTO;
import com.caffeine.gwanghwamun.domain.order.order_items.dto.OrderItemResDTO;
import com.caffeine.gwanghwamun.domain.order.order_items.dto.OrderMenuItemReqDto;
import com.caffeine.gwanghwamun.domain.order.order_items.entity.OrderItem;
import com.caffeine.gwanghwamun.domain.order.order_items.order_item_options.entity.OrderItemOption;
import com.caffeine.gwanghwamun.domain.order.order_items.order_item_options.repository.OrderItemOptionRepository;
import com.caffeine.gwanghwamun.domain.order.order_items.repository.OrderItemRepository;
import com.caffeine.gwanghwamun.domain.order.order_status_log.repository.OrderStatusLogRepository;
import com.caffeine.gwanghwamun.domain.order.repository.OrderRepository;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.store.repository.StoreRepository;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.entity.UserRoleEnum;
import com.caffeine.gwanghwamun.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
  private final OrderItemOptionRepository orderItemOptionRepository;
  private final MenuOptionRepository menuOptionRepository;

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

    User user = userRepository
        .findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    Store store =
        storeRepository
            .findActiveById(req.storeId())
            .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

    int totalPrice = 0;

    Order order = Order.builder()
        .store(store)
        .user(user)
        .orderStatus(OrderStatus.ORDER_WAITING)
        .deliveryAddress(req.address())
        .totalPrice(totalPrice)
        .build();

    orderRepository.save(order);

//    if (req.cartMode() == CartMode.CART) {
//      List<Cart> cartItemList = cartRepository.findByUserAndStoreAndDeletedDateIsNull(user, store);
//      cartItemList.stream().map()
//    } else {
    List<OrderItemListResDTO> orderItemResList = saveOrderItem(req.menuItemList(), order);
//    }
    totalPrice = orderItemResList.stream()
        .mapToInt(OrderItemListResDTO::totalPrice)
        .sum();

    order.updateTotalPrice(totalPrice);

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

    order.cancel();

    return new OrderStatusResDTO(order);
  }

  @Transactional
  public OrderStatusResDTO acceptOrder(User user, UUID orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

    validateOrderAccess(user, order);

    order.accept();

    return new OrderStatusResDTO(order);
  }

  @Transactional
  public OrderStatusResDTO rejectOrder(User user, UUID orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

    validateOrderAccess(user, order);

    order.reject();

    return new OrderStatusResDTO(order);
  }

  @Transactional
  public OrderStatusResDTO completeCooking(User user, UUID orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

    validateOrderAccess(user, order);

    order.completeCooking();

    return new OrderStatusResDTO(order);
  }

  @Transactional
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

  @Transactional
  private List<OrderItemListResDTO> saveOrderItem(List<OrderMenuItemReqDto> menuList, Order order) {

    List<OrderItemListResDTO> orderItemInfoList = new ArrayList<>();

    for (OrderMenuItemReqDto item : menuList) {

      Menu menu = menuRepository.findById(item.menuItemId())
          .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

      if (menu.getIsSoldOut() || menu.getIsHidden()) {
        throw new CustomException(ErrorCode.ORDER_UNABLE_MENU);
      }

      List<MenuOption> menuOptions = menuOptionRepository.findAllByIdAndMenu(item.menuOptionList(), menu);

      for (MenuOption option : menuOptions) {
        if (option.getIsHidden() || option.getIsSoldOut()) {
          throw new CustomException(ErrorCode.ORDER_UNABLE_MENU_OPTION);
        }
      }

      int optionPrice = menuOptions.stream()
          .mapToInt(MenuOption::getPrice)
          .sum();

      int itemTotalPrice = (menu.getPrice() + optionPrice) * item.quantity();

      OrderItem orderItem = OrderItem.builder()
          .order(order)
          .menu(menu)
          .quantity(item.quantity())
          .price(menu.getPrice())
          .build();

      List<OrderItemOption> orderItemOptions = menuOptions.stream()
          .map(option -> OrderItemOption.builder()
              .orderItem(orderItem)
              .menuOption(option)
              .optionPrice(option.getPrice())
              .build())
          .toList();

      orderItemRepository.save(orderItem);
      orderItemOptionRepository.saveAll(orderItemOptions);

      orderItemInfoList.add(new OrderItemListResDTO(orderItem, orderItemOptions, itemTotalPrice));
    }
    return orderItemInfoList;
  }
}