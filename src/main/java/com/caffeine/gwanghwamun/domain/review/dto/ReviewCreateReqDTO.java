package com.caffeine.gwanghwamun.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewCreateReqDTO(
		@Size(max = 1000) String content,
		@NotNull(message = "평점은 필수입니다.")
				@Min(value = 1, message = "평점은 최소 1점입니다")
				@Max(value = 5, message = "평점은 최대 5점입니다")
				Long rating,
		String gid) {}
