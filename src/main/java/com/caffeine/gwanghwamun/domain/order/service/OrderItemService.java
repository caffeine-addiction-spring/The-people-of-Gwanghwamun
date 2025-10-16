package com.caffeine.gwanghwamun.domain.order.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import com.caffeine.gwanghwamun.domain.menu.entity.MenuOption;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuOptionRepository;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuRepository;
import com.caffeine.gwanghwamun.domain.order.entity.Order;
import com.caffeine.gwanghwamun.domain.order.dto.OrderItemListResDTO;
import com.caffeine.gwanghwamun.domain.order.dto.OrderMenuItemReqDTO;
import com.caffeine.gwanghwamun.domain.order.entity.OrderItem;
import com.caffeine.gwanghwamun.domain.order.entity.OrderItemOption;
import com.caffeine.gwanghwamun.domain.order.repository.OrderItemOptionRepository;
import com.caffeine.gwanghwamun.domain.order.repository.OrderItemRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {

  private final OrderItemRepository orderItemRepository;
  private final OrderItemOptionRepository orderItemOptionRepository;
  private final MenuRepository menuRepository;
  private final MenuOptionRepository menuOptionRepository;

  @Transactional
  public List<OrderItemListResDTO> saveOrderItem(List<OrderMenuItemReqDTO> menuList, Order order) {

    List<OrderItemListResDTO> orderItemInfoList = new ArrayList<>();

    for (OrderMenuItemReqDTO item : menuList) {

      Menu menu =
          menuRepository
              .findById(item.menuItemId())
              .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

      if (menu.isSoldOut() || menu.isHidden()) {
        throw new CustomException(ErrorCode.ORDER_UNABLE_MENU);
      }

      List<MenuOption> menuOptions =
          menuOptionRepository.findAllByMenuOptionIdInAndMenuId(
              item.menuOptionList(), menu.getMenuId());

      for (MenuOption option : menuOptions) {
        if (option.isSoldOut() || option.isHidden()) {
          throw new CustomException(ErrorCode.ORDER_UNABLE_MENU_OPTION);
        }
      }

      int optionPrice = menuOptions.stream().mapToInt(MenuOption::getPrice).sum();

      int itemTotalPrice = (menu.getPrice() + optionPrice) * item.quantity();

      OrderItem orderItem =
          OrderItem.builder()
              .order(order)
              .menu(menu)
              .menuName(menu.getName())
              .quantity(item.quantity())
              .price(menu.getPrice())
              .build();

      List<OrderItemOption> orderItemOptions =
          menuOptions.stream()
              .map(
                  option ->
                      OrderItemOption.builder()
                          .orderItem(orderItem)
                          .menuOption(option)
                          .optionName(option.getOptionName())
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
