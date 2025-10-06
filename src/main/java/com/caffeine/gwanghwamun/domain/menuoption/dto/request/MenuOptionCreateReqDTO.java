package com.caffeine.gwanghwamun.domain.menuoption.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record MenuOptionCreateReqDTO(
		@NotBlank @Size(max = 100) String optionName,
		@NotNull @PositiveOrZero Integer price,
		@Size(max = 2000) String content,
		Boolean isHidden,
		Boolean isSoldOut) {}
