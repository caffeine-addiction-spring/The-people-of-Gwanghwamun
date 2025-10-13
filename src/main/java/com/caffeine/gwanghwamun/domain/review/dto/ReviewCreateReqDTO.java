package com.caffeine.gwanghwamun.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewCreateReqDTO(
        @Size(max = 1000) String content,
        @NotNull Long rating
) {
}
