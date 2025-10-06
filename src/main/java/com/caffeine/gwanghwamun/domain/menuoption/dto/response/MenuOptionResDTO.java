package com.caffeine.gwanghwamun.domain.menuoption.dto.response;

import com.caffeine.gwanghwamun.domain.menuoption.entity.MenuOption;
import java.time.LocalDateTime;
import java.util.UUID;

public record MenuOptionResDTO(
		UUID optionId,
		UUID menuId,
		String optionName,
		Integer price,
		String content,
		Boolean isHidden,
		Boolean isSoldOut,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {
	public MenuOptionResDTO(MenuOption option) {
		this(
				option.getMenuOptionId(),
				option.getMenuId(),
				option.getOptionName(),
				option.getPrice(),
				option.getContent(),
				option.getIsHidden(),
				option.getIsSoldOut(),
				option.getCreateAt(),
				option.getLastUpdatedAt());
	}
}
