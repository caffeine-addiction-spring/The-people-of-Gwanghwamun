package com.caffeine.gwanghwamun.domain.order.order_items.dto;

import com.caffeine.gwanghwamun.domain.menu.entity.Menu;

import java.util.List;
import java.util.UUID;

public record OrderMenuItemReqDto(
    UUID menuItemId,
    List<UUID> menuOptionList,
    Integer quantity
) {
  public OrderMenuItemReqDto(Menu menu, List<UUID> menuOptionList, Integer quantity) {
    this(
        menu.getMenuId(),
        menuOptionList,
        quantity
    );

  }
}
