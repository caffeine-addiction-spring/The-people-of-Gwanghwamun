package com.caffeine.gwanghwamun.domain.order.dto;

import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import jakarta.validation.constraints.Min;

import java.util.List;
import java.util.UUID;

public record OrderMenuItemReqDTO(UUID menuItemId, List<UUID> menuOptionList, Integer quantity) {
  public OrderMenuItemReqDTO(
      Menu menu,
      List<UUID> menuOptionList,
      @Min(value = 0, message = "수량은 0 이상이어야 합니다.") Integer quantity) {
    this(menu.getMenuId(), menuOptionList, quantity);
  }
}
