package com.caffeine.gwanghwamun.domain.menu.dto.response;

import com.caffeine.gwanghwamun.domain.file.dto.FileInfoResDTO;
import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import com.caffeine.gwanghwamun.domain.menu.entity.MenuCategory;
import java.time.LocalDateTime;
import java.util.List;
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
		String groupId,
		List<FileInfoResDTO> images,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {
	public MenuResDTO(Menu menu) {
		this(
				menu.getMenuId(),
				menu.getStoreId(),
				menu.getName(),
				menu.getPrice(),
				menu.getMenuContent(),
				menu.isHidden(),
				menu.isSoldOut(),
				menu.getMenuCategory(),
				menu.getGroupId(),
				null,
				menu.getCreateAt(),
				menu.getLastUpdatedAt());
	}

	public MenuResDTO(Menu menu, List<FileInfoResDTO> images) {
		this(
				menu.getMenuId(),
				menu.getStoreId(),
				menu.getName(),
				menu.getPrice(),
				menu.getMenuContent(),
				menu.isHidden(),
				menu.isSoldOut(),
				menu.getMenuCategory(),
				menu.getGroupId(),
				images,
				menu.getCreateAt(),
				menu.getLastUpdatedAt());
	}
}
