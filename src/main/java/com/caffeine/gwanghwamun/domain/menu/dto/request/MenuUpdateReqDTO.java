package com.caffeine.gwanghwamun.domain.menu.dto.request;

import com.caffeine.gwanghwamun.domain.menu.entity.MenuCategory;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record MenuUpdateReqDTO(
		@Size(max = 100) String name,
		@PositiveOrZero Integer price,
		@Size(max = 2000) String content,
		Boolean isHidden,
		Boolean isSoldOut,
		MenuCategory menuCategory,
		Long imageGroupId) {}
