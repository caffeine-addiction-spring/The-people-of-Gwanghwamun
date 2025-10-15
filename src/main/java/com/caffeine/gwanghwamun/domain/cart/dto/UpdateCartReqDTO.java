package com.caffeine.gwanghwamun.domain.cart.dto;

import jakarta.validation.constraints.Min;
import java.util.UUID;

public record UpdateCartReqDTO(
		UUID menuOptionId, @Min(value = 0, message = "수량은 0 이상이어야 합니다.") int quantity) {}
