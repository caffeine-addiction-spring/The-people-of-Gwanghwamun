package com.caffeine.gwanghwamun.domain.menuoption.dto.request;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record MenuOptionUpdateReqDTO(
		@Size(max = 100) String optionName,
		@PositiveOrZero Integer price,
		@Size(max = 2000) String content,
		Boolean isHidden,
		Boolean isSoldOut) {}
