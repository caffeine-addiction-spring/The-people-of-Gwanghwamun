package com.caffeine.gwanghwamun.domain.menu.dto.request;

import com.caffeine.gwanghwamun.domain.menu.entity.MenuCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record MenuCreateReqDTO(
		@NotBlank @Size(max = 100) String name,
		@NotNull @PositiveOrZero Integer price,
		@Size(max = 2000) String content,
		Boolean isHidden,
		Boolean useAI,
		@Size(max = 200) String aiPrompt,
		@NotNull MenuCategory menuCategory,
		Long imageGroupId) {}
