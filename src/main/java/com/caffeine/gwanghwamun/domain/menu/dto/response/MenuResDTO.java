package com.caffeine.gwanghwamun.domain.menu.dto.response;

import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import com.caffeine.gwanghwamun.domain.menu.entity.MenuCategory;
import java.time.LocalDateTime;
import java.util.UUID;

public record MenuResDTO(
		UUID menuId,
		UUID storeId,
		String name,
		Integer price,
		String content,
		Boolean isHidden,
		Boolean isSoldOut,
		MenuCategory menuCategory,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {
	public MenuResDTO(Menu menu) {
		this(
				menu.getMenuId(),
				menu.getStoreId(),
				menu.getName(),
				menu.getPrice(),
				menu.getMenuContent(),
				menu.getIsHidden(),
				menu.getIsSoldOut(),
				menu.getMenuCategory(),
				menu.getCreateAt(),
				menu.getLastUpdatedAt());
	}
}
