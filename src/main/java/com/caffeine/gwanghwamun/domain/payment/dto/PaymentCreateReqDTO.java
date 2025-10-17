package com.caffeine.gwanghwamun.domain.payment.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentCreateReqDTO(@NotNull Integer amount) {}
